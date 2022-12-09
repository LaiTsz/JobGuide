package com.project.job_guide.ui.home

import androidx.lifecycle.*
import com.project.job_guide.data.Result
import com.project.job_guide.data.db.entity.UserInfo
import com.project.job_guide.data.db.entity.UserNotification
import com.project.job_guide.data.db.remote.FirebaseAuthStateObserver
import com.project.job_guide.data.db.remote.FirebaseReferenceValueObserver
import com.project.job_guide.data.db.repository.AuthRepository

import com.project.job_guide.data.db.repository.DatabaseRepository
import com.project.job_guide.ui.DefaultViewModel
import com.google.firebase.auth.FirebaseUser

class JobViewModelFactory(private val myUserID: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return JobViewModel(myUserID) as T
    }
}

class JobViewModel(private val myUserID: String) : DefaultViewModel() {
    private val dbRepository: DatabaseRepository = DatabaseRepository()
    private val firebaseReferenceObserver = FirebaseReferenceValueObserver()
    private val _userNotificationsList = MutableLiveData<MutableList<UserNotification>>()
    private val fbRefNotificationsObserver = FirebaseReferenceValueObserver()
    private val authRepository = AuthRepository()
    private val fbAuthStateObserver = FirebaseAuthStateObserver()
    var userNotificationsList: LiveData<MutableList<UserNotification>> = _userNotificationsList
    private val _userInfo: MutableLiveData<UserInfo> = MutableLiveData()
    val userInfo: LiveData<UserInfo> = _userInfo

    init {
        loadAndObserveUserInfo()
        setupAuthObserver()
    }
    private fun loadAndObserveUserInfo() {
        dbRepository.loadAndObserveUserInfo(myUserID, firebaseReferenceObserver)
        { result: Result<UserInfo> -> onResult(_userInfo, result) }
    }
    private fun setupAuthObserver(){
        authRepository.observeAuthState(fbAuthStateObserver) { result: Result<FirebaseUser> ->
            if (result is Result.Success) {
                startObservingNotifications()
            } else {
                stopObservingNotifications()
            }
        }
    }

    private fun startObservingNotifications() {
        dbRepository.loadAndObserveUserNotifications(myUserID, fbRefNotificationsObserver) { result: Result<MutableList<UserNotification>> ->
            if (result is Result.Success) {
                _userNotificationsList.value = result.data
            }
        }
    }
    private fun stopObservingNotifications() {
        fbRefNotificationsObserver.clear()
    }
}
