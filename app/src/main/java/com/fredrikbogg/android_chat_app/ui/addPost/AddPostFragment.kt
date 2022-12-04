package com.fredrikbogg.android_chat_app.ui.addPost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.databinding.FragmentAddPostBinding
import com.fredrikbogg.android_chat_app.databinding.FragmentProfileBinding
import com.fredrikbogg.android_chat_app.ui.start.StartViewModel


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
}