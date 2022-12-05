package com.fredrikbogg.android_chat_app.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fredrikbogg.android_chat_app.data.db.entity.Comment
import com.fredrikbogg.android_chat_app.data.db.entity.Post
import com.fredrikbogg.android_chat_app.databinding.ListItemForumBinding
import com.fredrikbogg.android_chat_app.databinding.ListItemPostBinding
import com.fredrikbogg.android_chat_app.ui.forum.ForumListAdapter
import com.fredrikbogg.android_chat_app.ui.forum.ForumViewModel


class PostListAdapter internal constructor(private val viewModel: PostViewModel) :
    ListAdapter<Comment, PostListAdapter.ViewHolder>(PostDiffCallback()) {

    class ViewHolder(private val binding: ListItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: PostViewModel, item: Comment) {
            binding.viewmodel = viewModel
            binding.comment = item
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemPostBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.context == newItem.context
    }
}