package com.fredrikbogg.android_chat_app.ui.forum

import android.util.Log
import androidx.lifecycle.*
import com.fredrikbogg.android_chat_app.data.db.repository.DatabaseRepository
import com.fredrikbogg.android_chat_app.data.Event
import com.fredrikbogg.android_chat_app.data.Result
import com.fredrikbogg.android_chat_app.data.db.entity.Post
import com.fredrikbogg.android_chat_app.data.db.remote.FirebaseReferenceChildObserver
import com.fredrikbogg.android_chat_app.ui.DefaultViewModel
import com.google.firebase.database.*
import kotlin.math.E


class ForumViewModel: DefaultViewModel() {
    private val repository: DatabaseRepository = DatabaseRepository()
    private val _selectedPost = MutableLiveData<Event<Post>>()
    var selectedPost: LiveData<Event<Post>> = _selectedPost
    private val updatedForumList = MutableLiveData<MutableList<Post>>()
    val forumList = MediatorLiveData<List<Post>>()

    init {
        forumList.addSource(updatedForumList){
            forumList.value = updatedForumList.value
        }
        loadForum()
        Log.e("forumList","${forumList.value}")

    }

    fun loadForum() {
        repository.loadForum { result: Result<MutableList<Post>> ->
            onResult(updatedForumList, result)
        }
    }
    fun selectForum(post: Post){
        _selectedPost.value = Event(post)
    }

}