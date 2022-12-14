package com.project.job_guide.ui.start.createAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.job_guide.data.Event
import com.project.job_guide.data.Result
import com.project.job_guide.data.db.entity.User
import com.project.job_guide.data.db.repository.AuthRepository
import com.project.job_guide.data.db.repository.DatabaseRepository
import com.project.job_guide.data.model.CreateUser
import com.project.job_guide.ui.DefaultViewModel
import com.project.job_guide.util.isEmailValid
import com.project.job_guide.util.isTextValid
import com.google.firebase.auth.FirebaseUser

class CreateAccountViewModel : DefaultViewModel() {

    private val dbRepository = DatabaseRepository()
    private val authRepository = AuthRepository()
    private val mIsCreatedEvent = MutableLiveData<Event<FirebaseUser>>()

    val isCreatedEvent: LiveData<Event<FirebaseUser>> = mIsCreatedEvent
    val displayFirstNameText = MutableLiveData<String>() // Two way
    val displayLastNameText = MutableLiveData<String>() // Two way
    var displayNameText:String = "" // Two way
    val emailText = MutableLiveData<String>() // Two way
    val passwordText = MutableLiveData<String>() // Two way
    val isCreatingAccount = MutableLiveData<Boolean>()

    private fun createAccount() {
        isCreatingAccount.value = true
        val createUser =
            CreateUser(displayNameText, emailText.value!!, passwordText.value!!)

        authRepository.createUser(createUser) { result: Result<FirebaseUser> ->
            onResult(null, result)
            if (result is Result.Success) {
                mIsCreatedEvent.value = Event(result.data!!)
                dbRepository.updateNewUser(User().apply {
                    info.id = result.data.uid
                    info.displayName = createUser.displayName
                })
            }
            if (result is Result.Success || result is Result.Error) isCreatingAccount.value = false
        }
    }

    fun createAccountPressed() {
        if (!isTextValid(1, displayFirstNameText.value)) {
            mSnackBarText.value = Event("First name is too short")
            return
        }
        if (!isTextValid(1, displayLastNameText.value)) {
            mSnackBarText.value = Event("Last name is too short")
            return
        }
        if (!isEmailValid(emailText.value.toString())) {
            mSnackBarText.value = Event("Invalid email format")
            return
        }
        if (!isTextValid(6, passwordText.value)) {
            mSnackBarText.value = Event("Password is too short")
            return
        }
        displayNameText=displayLastNameText.value+" "+ displayFirstNameText.value
        createAccount()
    }
}