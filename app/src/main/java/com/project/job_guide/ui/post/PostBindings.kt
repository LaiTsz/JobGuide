package com.project.job_guide.ui.post

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.job_guide.data.db.entity.Comment

@BindingAdapter("bind_Post_list")
fun bindPostList(listView: RecyclerView, items: List<Comment>?) {
    items?.let { (listView.adapter as PostListAdapter).submitList(items) }
}

