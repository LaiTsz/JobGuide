package com.fredrikbogg.android_chat_app.ui.forum

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fredrikbogg.android_chat_app.data.db.entity.Post
import com.fredrikbogg.android_chat_app.data.db.entity.User

@BindingAdapter("bind_forum_list")
fun bindForumList(listView: RecyclerView, items: List<Post>?) {
    items?.let { (listView.adapter as ForumListAdapter).submitList(items) }
}

