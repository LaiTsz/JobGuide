package com.fredrikbogg.android_chat_app.ui.changesetting

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.data.Event
import com.fredrikbogg.android_chat_app.data.Result
import com.fredrikbogg.android_chat_app.data.db.entity.UserInfo
import com.fredrikbogg.android_chat_app.data.db.remote.FirebaseReferenceValueObserver
import com.fredrikbogg.android_chat_app.data.db.repository.AuthRepository
import com.fredrikbogg.android_chat_app.data.db.repository.DatabaseRepository
import com.fredrikbogg.android_chat_app.data.db.repository.StorageRepository
import com.fredrikbogg.android_chat_app.ui.DefaultViewModel

class ChangeSettingViewModelFactory(private val userID: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChangeSettingViewModel(userID) as T
    }
}
class ChangeSettingViewModel(private val userID: String) :DefaultViewModel() {
    private val dbRepository: DatabaseRepository = DatabaseRepository()
    private val storageRepository = StorageRepository()
    private val authRepository = AuthRepository()

    private val _userInfo: MutableLiveData<UserInfo> = MutableLiveData()
    val userInfo: LiveData<UserInfo> = _userInfo

        //MutableLiveData<String>() // Two way
    private val _editStatusEvent = MutableLiveData<Event<Unit>>()
    val editStatusEvent: LiveData<Event<Unit>> = _editStatusEvent

    private val _editImageEvent = MutableLiveData<Event<Unit>>()
    val editImageEvent: LiveData<Event<Unit>> = _editImageEvent
    private val firebaseReferenceObserver = FirebaseReferenceValueObserver()
    private val _displayName = MutableLiveData<String>()
    val displayName: LiveData<String> = _displayName

    init {
        loadAndObserveUserInfo()
    }

    override fun onCleared() {
        super.onCleared()
        firebaseReferenceObserver.clear()
    }

    private fun loadAndObserveUserInfo() {
        dbRepository.loadAndObserveUserInfo(userID, firebaseReferenceObserver)
        { result: Result<UserInfo> -> onResult(_userInfo, result) }
    }

    fun changeUserStatus(status: String) {
        dbRepository.updateUserStatus(userID, status)
    }

    fun changeUserMajor(major:String){
        dbRepository.updateUserMajor(userID,major)
    }

    fun changeUserImage(byteArray: ByteArray) {
        storageRepository.updateUserProfileImage(userID, byteArray) { result: Result<Uri> ->
            onResult(null, result)
            if (result is Result.Success) {
                dbRepository.updateUserProfileImageUrl(userID, result.data.toString())
            }
        }
    }

    fun changeUserImagePressed() {
        _editImageEvent.value = Event(Unit)
    }

    fun changeUserStatusPressed() {
        _editStatusEvent.value = Event(Unit)
    }




}