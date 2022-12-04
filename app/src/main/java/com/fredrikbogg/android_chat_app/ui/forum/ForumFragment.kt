package com.fredrikbogg.android_chat_app.ui.forum

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.data.EventObserver
import com.fredrikbogg.android_chat_app.databinding.FragmentForumBinding
import com.fredrikbogg.android_chat_app.databinding.FragmentUsersBinding
import com.fredrikbogg.android_chat_app.ui.addPost.AddPostViewModel
import com.fredrikbogg.android_chat_app.ui.profile.ProfileFragment
import com.fredrikbogg.android_chat_app.ui.users.UsersListAdapter

class ForumFragment : Fragment() {
    private val viewModel by viewModels<ForumViewModel>()
    private lateinit var viewDataBinding: FragmentForumBinding
    private lateinit var listAdapter: ForumListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewDataBinding= FragmentForumBinding.inflate(inflater, container, false).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.forum_toolbar, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_post -> {
                findNavController().navigate(R.id.action_navigation_forum_to_addPost)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
        setupObservers()
    }
    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = ForumListAdapter(viewModel)
            viewDataBinding.forumRecyclerView.adapter = listAdapter
        } else {
            throw Exception("The viewmodel is not initialized")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadForum()
    }
    private fun setupObservers() {
        viewModel.selectedPost.observe(viewLifecycleOwner, EventObserver { navigateToPost(it.id) })
        Log.e("clicked", viewModel.selectedPost.value.toString())
    }

    private fun navigateToPost(postID: String) {
        val bundle = bundleOf(ProfileFragment.ARGS_KEY_USER_ID to postID)
        findNavController().navigate(R.id.action_navigation_forum_to_post, bundle)
    }
}