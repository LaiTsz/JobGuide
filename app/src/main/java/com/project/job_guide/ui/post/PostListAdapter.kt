package com.project.job_guide.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.job_guide.data.db.entity.Comment
import com.project.job_guide.databinding.ListItemPostBinding


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