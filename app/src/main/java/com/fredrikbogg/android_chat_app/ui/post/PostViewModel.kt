package com.fredrikbogg.android_chat_app.ui.post

import androidx.lifecycle.*
import com.fredrikbogg.android_chat_app.App.Companion.myUserID
import com.fredrikbogg.android_chat_app.data.db.remote.FirebaseReferenceChildObserver
import com.fredrikbogg.android_chat_app.data.db.remote.FirebaseReferenceValueObserver
import com.fredrikbogg.android_chat_app.data.db.repository.DatabaseRepository
import com.fredrikbogg.android_chat_app.ui.DefaultViewModel
import com.fredrikbogg.android_chat_app.data.Result
import com.fredrikbogg.android_chat_app.data.db.entity.*
import com.fredrikbogg.android_chat_app.ui.chat.ChatViewModel
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

    private val fbRefMessagesChildObserver = FirebaseReferenceChildObserver()
    private val fbRefPostObserver = FirebaseReferenceValueObserver()

    val commentList = MediatorLiveData<MutableList<Comment>>()
    val newCommentText = MutableLiveData<String>()
    val postName = postID

    init {
        setupPost()
        checkAndUpdateLastComment()
    }

    override fun onCleared() {
        super.onCleared()
        fbRefMessagesChildObserver.clear()
        fbRefPostObserver.clear()
    }

    private fun checkAndUpdateLastComment() {
        dbRepository.loadPost(postID) { result: Result<Comment> ->
            if (result is Result.Success && result.data != null) {
                result.data.context.let {
                        dbRepository.updatePostLastComment(postID, it)
                    }
                }
            }
    }

    private fun setupPost() {
                loadAndObserveNewComment()
    }

    private fun loadAndObserveNewComment() {
        commentList.addSource(_addedComment) { commentList.addNewItem(it) }

        dbRepository.loadAndObserveComment(postID, fbRefPostObserver
        ) { result: Result<Comment> ->
            onResult(_addedComment, result)
        }
    }

    fun sendCommentPressed() {
        if (!newCommentText.value.isNullOrBlank()) {
            val newComment= Comment(myUserID, newCommentText.value!!)
            dbRepository.updateNewComment(postID, newComment)
            dbRepository.updatePostLastComment(postID, newComment.context)
            newCommentText.value = null
        }
    }
}