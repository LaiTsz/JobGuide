package com.fredrikbogg.android_chat_app.ui.users

import android.util.Log
import androidx.lifecycle.*
import com.fredrikbogg.android_chat_app.App.Companion.myUserID
import com.fredrikbogg.android_chat_app.data.db.entity.User
import com.fredrikbogg.android_chat_app.data.db.repository.DatabaseRepository
import com.fredrikbogg.android_chat_app.ui.DefaultViewModel
import com.fredrikbogg.android_chat_app.data.Event
import com.fredrikbogg.android_chat_app.data.Result


class UsersViewModelFactory(private val myUserID: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersViewModel(myUserID) as T
    }
}

class UsersViewModel(private val myUserID: String) : DefaultViewModel() {
    private val repository: DatabaseRepository = DatabaseRepository()
    private val _selectedUser = MutableLiveData<Event<User>>()
    var selectedUser: LiveData<Event<User>> = _selectedUser
    private val updatedUsersList = MutableLiveData<MutableList<User>>()
    val usersList = MediatorLiveData<List<User>>()
    private val filterText = MutableLiveData<String>()


    init {
        usersList.addSource(filterText){ mutableList ->
            if(filterText.value != ""){
                usersList.value = updatedUsersList.value?.filter {
                it.info.displayName.toLowerCase().contains(filterText.value.toString().toLowerCase())&&
                        it.info.id != myUserID
            }}
            else{
                usersList.value = updatedUsersList.value?.filter {false}
            }

        }
        loadUsers()
    }

    private fun loadUsers() {
        repository.loadUsers { result: Result<MutableList<User>> ->
            onResult(updatedUsersList, result)
        }
    }

    fun postText(text: String?){
        this.filterText.postValue(text)
    }
    fun endText(){
        filterText.value=""
    }
    fun selectUser(user: User) {
        _selectedUser.value = Event(user)
    }
}