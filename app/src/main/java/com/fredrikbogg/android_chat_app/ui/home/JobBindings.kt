@file:Suppress("unused")

package com.fredrikbogg.android_chat_app.ui.home

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.data.model.ChatWithUserInfo
import com.fredrikbogg.android_chat_app.data.db.entity.Message
import com.fredrikbogg.android_chat_app.ui.chats.ChatsListAdapter
import com.fredrikbogg.android_chat_app.ui.chats.ChatsViewModel

@BindingAdapter("bind_job_list")
fun bindChatsList(listView: RecyclerView, items: List<ChatWithUserInfo>?) {
    items?.let { (listView.adapter as ChatsListAdapter).submitList(items) }
}