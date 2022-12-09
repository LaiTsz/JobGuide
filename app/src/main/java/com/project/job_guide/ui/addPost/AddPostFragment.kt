package com.project.job_guide.ui.addPost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.project.job_guide.App
import com.project.job_guide.data.EventObserver
import com.project.job_guide.databinding.FragmentAddPostBinding
import com.project.job_guide.ui.post.PostFragment


class AddPostFragment : Fragment() {

    private val viewModel by viewModels<AddPostViewModel>()
    private lateinit var viewDataBinding: FragmentAddPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding = FragmentAddPostBinding.inflate(inflater, container, false)
            .apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservers()
    }
    private fun setupObservers() {
        viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
            val bundle = bundleOf(
                PostFragment.ARGS_KEY_POST_ID to viewModel.postID.value,
                PostFragment.ARGS_KEY_USER_ID to App.myUserID)
            findNavController().navigate(it,bundle)
        })
    }

}