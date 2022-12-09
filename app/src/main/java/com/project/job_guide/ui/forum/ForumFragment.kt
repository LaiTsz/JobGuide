package com.project.job_guide.ui.forum

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.project.job_guide.App
import com.project.job_guide.R
import com.project.job_guide.data.EventObserver
import com.project.job_guide.databinding.FragmentForumBinding
import com.project.job_guide.ui.post.PostFragment
import kotlinx.android.synthetic.main.fragment_forum.*

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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        floating_action_button.setOnClickListener{findNavController().navigate(R.id.action_navigation_forum_to_addPost)}
    }

   /* override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
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
    }*/

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
        viewModel.selectedPost.observe(viewLifecycleOwner, EventObserver { navigateToPost(it.id)
        Log.e("clicked",it.id)})
    }

    private fun navigateToPost(postID: String) {
        val bundle = bundleOf(
            PostFragment.ARGS_KEY_POST_ID to postID,
            PostFragment.ARGS_KEY_USER_ID to App.myUserID)
        findNavController().navigate(R.id.action_navigation_forum_to_post, bundle)
    }
}