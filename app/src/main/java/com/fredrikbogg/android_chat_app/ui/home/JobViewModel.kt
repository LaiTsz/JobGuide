package com.fredrikbogg.android_chat_app.ui.home

import androidx.lifecycle.*
import com.fredrikbogg.android_chat_app.data.Job

import com.fredrikbogg.android_chat_app.data.db.repository.DatabaseRepository
import com.fredrikbogg.android_chat_app.data.db.repository.StorageRepository
import com.fredrikbogg.android_chat_app.ui.DefaultViewModel

class JobViewModel() : DefaultViewModel() {
    private val dbRepository: DatabaseRepository = DatabaseRepository()
    private val storageRepository = StorageRepository()
    private val jobList = MutableLiveData<MutableList<Job>>()
    private val talkList = MutableLiveData<MutableList<Job>>()


}