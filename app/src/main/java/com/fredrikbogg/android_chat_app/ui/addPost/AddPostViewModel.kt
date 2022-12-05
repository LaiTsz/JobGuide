package com.fredrikbogg.android_chat_app.ui.addPost

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fredrikbogg.android_chat_app.App.Companion.myUserID
import com.fredrikbogg.android_chat_app.data.Result
import com.fredrikbogg.android_chat_app.data.db.entity.Post
import com.fredrikbogg.android_chat_app.data.db.entity.User
import com.fredrikbogg.android_chat_app.data.db.entity.UserInfo
import com.fredrikbogg.android_chat_app.data.db.remote.FirebaseReferenceChildObserver
import com.fredrikbogg.android_chat_app.data.db.remote.FirebaseReferenceValueObserver
import com.fredrikbogg.android_chat_app.data.db.repository.DatabaseRepository
import com.fredrikbogg.android_chat_app.ui.DefaultViewModel

class AddPostViewModel: DefaultViewModel() {
    private val dbRepository :DatabaseRepository= DatabaseRepository()
    val topicText = MutableLiveData<String>()
    private val postID = MutableLiveData<String>()
    private val newPost = MutableLiveData<Post>()

    fun submitButtonPressed(){
        newPost.value= Post().apply {
            lastComment = ""
            topic = topicText.value.toString()
        }
        postID.value = dbRepository.updateNewPost(newPost.value!!)

    }
}