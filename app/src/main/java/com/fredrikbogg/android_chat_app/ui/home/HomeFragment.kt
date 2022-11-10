package com.fredrikbogg.android_chat_app.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fredrikbogg.android_chat_app.R


class HomeFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {
            setHasOptionsMenu(true)
            return inflater.inflate(R.layout.fragment_home, container, false)
        }

        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
            inflater.inflate(R.menu.home_toolbar, menu)
        }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.notification_request -> {
                    findNavController().navigate(R.id.action_navigation_home_to_notificationsFragment)
                    return true
                }
            }
            return super.onOptionsItemSelected(item)
        }
}