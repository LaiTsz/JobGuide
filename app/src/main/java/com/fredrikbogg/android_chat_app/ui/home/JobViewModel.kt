package com.fredrikbogg.android_chat_app.ui.home

import android.net.Uri
import androidx.lifecycle.*
import com.fredrikbogg.android_chat_app.App.Companion.myUserID
import com.fredrikbogg.android_chat_app.data.Event
import com.fredrikbogg.android_chat_app.data.Result
import com.fredrikbogg.android_chat_app.data.db.entity.Chat
import com.fredrikbogg.android_chat_app.data.model.ChatWithUserInfo
import com.fredrikbogg.android_chat_app.data.db.entity.UserFriend
import com.fredrikbogg.android_chat_app.data.db.entity.UserInfo
import com.fredrikbogg.android_chat_app.data.db.remote.FirebaseReferenceValueObserver
import com.fredrikbogg.android_chat_app.data.db.repository.AuthRepository
import com.fredrikbogg.android_chat_app.data.db.repository.DatabaseRepository
import com.fredrikbogg.android_chat_app.data.db.repository.StorageRepository
import com.fredrikbogg.android_chat_app.ui.DefaultViewModel
import com.fredrikbogg.android_chat_app.ui.chats.ChatsViewModel
import com.fredrikbogg.android_chat_app.util.addNewItem
import com.fredrikbogg.android_chat_app.util.convertTwoUserIDs
import com.fredrikbogg.android_chat_app.util.updateItemAt


class JobViewModel() : DefaultViewModel() {
    private val dbRepository: DatabaseRepository = DatabaseRepository()
    private val storageRepository = StorageRepository()


}