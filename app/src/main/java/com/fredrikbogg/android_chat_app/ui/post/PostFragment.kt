package com.fredrikbogg.android_chat_app.ui.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.databinding.FragmentPostBinding
import com.fredrikbogg.android_chat_app.databinding.ToolbarAddonPostBinding
import kotlinx.android.synthetic.main.fragment_post.*


class PostFragment : Fragment() {

    companion object {
        const val ARGS_KEY_POST_ID = "postID"
        const val ARGS_KEY_USER_ID = "userID"

    }
    private val viewModel: PostViewModel by viewModels {
        PostViewModelFactory(
            requireArguments().getString(ARGS_KEY_USER_ID)!!,
            requireArguments().getString(ARGS_KEY_POST_ID)!!
        )
    }
    private lateinit var viewDataBinding: FragmentPostBinding
    private lateinit var listAdapter: PostListAdapter
    private lateinit var listAdapterObserver: RecyclerView.AdapterDataObserver
    private lateinit var toolbarAddonPostBinding: ToolbarAddonPostBinding

    override fun onPause() {
        super.onPause()
        removeCustomToolbar()
    }

    override fun onResume() {
        super.onResume()
        setupCustomToolbar()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewDataBinding =
            FragmentPostBinding.inflate(inflater, container, false).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)

        toolbarAddonPostBinding =
            ToolbarAddonPostBinding.inflate(inflater, container, false)
                .apply { viewmodel = viewModel }
        toolbarAddonPostBinding.lifecycleOwner = this.viewLifecycleOwner

        return viewDataBinding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
        setupCustomToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.action_postFragment_to_navigation_forum)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun removeCustomToolbar() {
        val supportActionBar = (activity as AppCompatActivity?)!!.supportActionBar
        supportActionBar!!.setDisplayShowCustomEnabled(false)
        supportActionBar.setDisplayShowTitleEnabled(true)
        supportActionBar.customView = null
    }

    private fun setupCustomToolbar() {
        val supportActionBar = (activity as AppCompatActivity?)!!.supportActionBar
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar.setDisplayShowTitleEnabled(false)
        supportActionBar.customView = toolbarAddonPostBinding.root
    }
    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapterObserver = (object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    commentRecyclerView.scrollToPosition(positionStart)
                }
            })
            listAdapter =
                PostListAdapter(viewModel)
            listAdapter.registerAdapterDataObserver(listAdapterObserver)
            viewDataBinding.commentRecyclerView.adapter = listAdapter
        } else {
            throw Exception("The viewmodel is not initialized")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        listAdapter.unregisterAdapterDataObserver(listAdapterObserver)
    }

}