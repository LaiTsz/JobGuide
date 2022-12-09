package com.project.job_guide.ui.addPost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.job_guide.R
import com.project.job_guide.data.Event
import com.project.job_guide.data.db.entity.Post
import com.project.job_guide.data.db.repository.DatabaseRepository
import com.project.job_guide.ui.DefaultViewModel

class AddPostViewModel: DefaultViewModel() {
    private val dbRepository :DatabaseRepository= DatabaseRepository()
    val topicText = MutableLiveData<String>()
    val postID = MutableLiveData<String>()
    private val newPost = MutableLiveData<Post>()
    private val _navigateScreen = MutableLiveData<Event<Int>>()
    val navigateScreen: LiveData<Event<Int>> = _navigateScreen

    fun submitButtonPressed(){
        newPost.value= Post().apply {
            lastComment = ""
            topic = topicText.value.toString()

        }
        postID.value = dbRepository.updateNewPost(newPost.value!!)
        _navigateScreen.value = Event(R.id.action_addPostFragment_to_postFragment)
        }
}