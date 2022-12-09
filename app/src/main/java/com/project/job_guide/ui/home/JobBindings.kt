@file:Suppress("unused")

package com.project.job_guide.ui.home

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.job_guide.data.model.ChatWithUserInfo
import com.project.job_guide.ui.chats.ChatsListAdapter

@BindingAdapter("bind_job_list")
fun bindChatsList(listView: RecyclerView, items: List<ChatWithUserInfo>?) {
    items?.let { (listView.adapter as ChatsListAdapter).submitList(items) }
}