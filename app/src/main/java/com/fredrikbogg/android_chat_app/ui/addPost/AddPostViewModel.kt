package com.fredrikbogg.android_chat_app.ui.addPost

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fredrikbogg.android_chat_app.data.db.entity.Post
import com.fredrikbogg.android_chat_app.data.db.repository.DatabaseRepository

class AddPostViewModel: ViewModel() {
    private val dbRepository :DatabaseRepository= DatabaseRepository()
    val topicText = MutableLiveData<String>()
    val firstMessageText = MutableLiveData<String>()
    private val key = MutableLiveData<String>()

    fun submitButtonPressed(){
        Log.e("AddPost","${topicText.value}+${firstMessageText.value}")
        val newPost = Post().apply {
            lastMessage = firstMessageText.value.toString()
            topic = topicText.value.toString()
        }
        key.value = dbRepository.updateNewPost(newPost)
    }
}