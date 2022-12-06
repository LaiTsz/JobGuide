package com.fredrikbogg.android_chat_app.ui.chats

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fredrikbogg.android_chat_app.App
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.data.model.ChatWithUserInfo
import com.fredrikbogg.android_chat_app.databinding.FragmentChatsBinding
import com.fredrikbogg.android_chat_app.data.EventObserver
import com.fredrikbogg.android_chat_app.ui.chat.ChatFragment
import com.fredrikbogg.android_chat_app.util.convertTwoUserIDs

class ChatsFragment : Fragment() {

    private val viewModel: ChatsViewModel by viewModels { ChatsViewModelFactory(App.myUserID) }
    private lateinit var viewDataBinding: FragmentChatsBinding
    private lateinit var listAdapter: ChatsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentChatsBinding.inflate(inflater, container, false).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chats_toolbar, menu)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
        setupObservers()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_Friends -> {
                findNavController().navigate(R.id.action_navigation_chats_to_navigation_users)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = ChatsListAdapter(viewModel)
            viewDataBinding.chatsRecyclerView.adapter = listAdapter
        } else {
            throw Exception("The viewmodel is not initialized")
        }
    }


    private fun setupObservers() {
        viewModel.selectedChat.observe(viewLifecycleOwner,
            EventObserver { navigateToChat(it) })
    }

    private fun navigateToChat(chatWithUserInfo: ChatWithUserInfo) {
        val bundle = bundleOf(
            ChatFragment.ARGS_KEY_USER_ID to App.myUserID,
            ChatFragment.ARGS_KEY_OTHER_USER_ID to chatWithUserInfo.mUserInfo.id,
            ChatFragment.ARGS_KEY_CHAT_ID to convertTwoUserIDs(App.myUserID, chatWithUserInfo.mUserInfo.id)
        )
        findNavController().navigate(R.id.action_navigation_chats_to_chatFragment, bundle)
    }
}