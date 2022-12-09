package com.project.job_guide.ui.forum

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.job_guide.data.db.entity.Post

@BindingAdapter("bind_forum_list")
fun bindForumList(listView: RecyclerView, items: List<Post>?) {
    items?.let { (listView.adapter as ForumListAdapter).submitList(items) }
}

