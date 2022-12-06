package com.fredrikbogg.android_chat_app.ui.post

import androidx.lifecycle.*
import com.fredrikbogg.android_chat_app.data.db.remote.FirebaseReferenceChildObserver
import com.fredrikbogg.android_chat_app.data.db.remote.FirebaseReferenceValueObserver
import com.fredrikbogg.android_chat_app.data.db.repository.DatabaseRepository
import com.fredrikbogg.android_chat_app.ui.DefaultViewModel
import com.fredrikbogg.android_chat_app.data.Result
import com.fredrikbogg.android_chat_app.data.db.entity.*
import com.fredrikbogg.android_chat_app.util.addNewItem

class PostViewModelFactory(private val myUserID: String,private val postID: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(myUserID,postID) as T
    }
}

class PostViewModel(private val myUserID: String,private val postID: String) : DefaultViewModel() {

    private val dbRepository: DatabaseRepository = DatabaseRepository()
    private val _addedComment = MutableLiveData<Comment>()
    private val _userInfo: MutableLiveData<UserInfo> = MutableLiveData()
    val userInfo: LiveData<UserInfo> = _userInfo
    private val _postInfo: MutableLiveData<Post> = MutableLiveData()
    val postInfo: LiveData<Post> = _postInfo
    private val fbRefPostObserver = FirebaseReferenceChildObserver()
    private val firebaseReferenceObserver = FirebaseReferenceValueObserver()
    val commentList = MediatorLiveData<MutableList<Comment>>()
    val newCommentText = MutableLiveData<String>()

    init {
        setupPost()
        loadAndObserveUserInfo()
        loadAndObservePostInfo()
    }

    override fun onCleared() {
        super.onCleared()
        fbRefPostObserver.clear()
    }

    private fun loadAndObserveUserInfo() {
        dbRepository.loadAndObserveUserInfo(myUserID, firebaseReferenceObserver)
        { result: Result<UserInfo> -> onResult(_userInfo, result) }
    }

    private fun loadAndObservePostInfo() {
        dbRepository.loadAndObservePostInfo(postID, firebaseReferenceObserver)
        { result: Result<Post> -> onResult(_postInfo, result) }
    }

    private fun setupPost() {
        loadAndObserveNewComment()
    }

    private fun loadAndObserveNewComment() {
        commentList.addSource(_addedComment) { commentList.addNewItem(it) }
        dbRepository.loadAndObserveCommentAdded(postID, fbRefPostObserver
        ) { result: Result<Comment> ->
            onResult(_addedComment, result)
        }
    }

    fun sendCommentPressed() {
        if (!newCommentText.value.isNullOrBlank()) {
            var displayName = userInfo.value?.displayName
            val newComment= displayName?.let { Comment(it, newCommentText.value!!) }
            if (newComment != null) {
                dbRepository.updateNewComment(postID, newComment)
            }
            if (newComment != null) {
                dbRepository.updatePostLastComment(postID, newComment.context)
            }
            newCommentText.value = null
        }
    }
}