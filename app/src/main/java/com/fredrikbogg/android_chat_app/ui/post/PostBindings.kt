package com.fredrikbogg.android_chat_app.ui.post

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fredrikbogg.android_chat_app.data.db.entity.Comment
import com.fredrikbogg.android_chat_app.data.db.entity.Post
import com.fredrikbogg.android_chat_app.ui.forum.ForumListAdapter

@BindingAdapter("bind_Post_list")
fun bindPostList(listView: RecyclerView, items: List<Comment>?) {
    items?.let { (listView.adapter as PostListAdapter).submitList(items) }
}

