package com.project.job_guide.ui.forum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.job_guide.data.db.entity.Post
import com.project.job_guide.databinding.ListItemForumBinding


class ForumListAdapter internal constructor(private val viewModel: ForumViewModel) :
    ListAdapter<Post, ForumListAdapter.ViewHolder>(ForumDiffCallback()) {

    class ViewHolder(private val binding: ListItemForumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: ForumViewModel, item: Post) {
            binding.viewmodel = viewModel
            binding.post = item
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemForumBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }
}

class ForumDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.topic == newItem.topic
    }
}