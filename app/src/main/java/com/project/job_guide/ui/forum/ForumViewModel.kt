package com.project.job_guide.ui.forum

import android.util.Log
import androidx.lifecycle.*
import com.project.job_guide.data.db.repository.DatabaseRepository
import com.project.job_guide.data.Event
import com.project.job_guide.data.Result
import com.project.job_guide.data.db.entity.Post
import com.project.job_guide.ui.DefaultViewModel


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