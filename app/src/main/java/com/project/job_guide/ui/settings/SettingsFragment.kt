package com.project.job_guide.ui.settings

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.project.job_guide.App
import com.project.job_guide.R
import com.project.job_guide.databinding.FragmentSettingsBinding
import com.project.job_guide.data.EventObserver
import com.project.job_guide.util.SharedPreferencesUtil
import com.project.job_guide.util.convertFileToByteArray


class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by viewModels { SettingsViewModelFactory(App.myUserID) }

    private lateinit var viewDataBinding: FragmentSettingsBinding
    private val selectImageIntentRequestCode = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentSettingsBinding.inflate(inflater, container, false)
            .apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)
        viewDataBinding.changeSettingButton.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_settings_changeSettingFragment)}
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservers()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == selectImageIntentRequestCode) {
            data?.data?.let { uri ->
                convertFileToByteArray(requireContext(), uri).let {
                    viewModel.changeUserImage(it)
                }
            }
        }
    }

    private fun setupObservers() {

        viewModel.logoutEvent.observe(viewLifecycleOwner,
            EventObserver {
                SharedPreferencesUtil.removeUserID(requireContext())
                navigateToStart()
            })


    }

    private fun navigateToStart() {
        findNavController().navigate(R.id.action_navigation_settings_to_startFragment)
    }

}