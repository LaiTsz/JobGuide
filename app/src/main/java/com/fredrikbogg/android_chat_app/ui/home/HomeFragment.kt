package com.fredrikbogg.android_chat_app.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.data.EventObserver
import com.fredrikbogg.android_chat_app.data.Job
import com.fredrikbogg.android_chat_app.data.db.repository.DatabaseRepository
import com.fredrikbogg.android_chat_app.databinding.FragmentHomeBinding
import com.fredrikbogg.android_chat_app.databinding.FragmentSettingsBinding
import com.fredrikbogg.android_chat_app.ui.settings.SettingsViewModel
import com.fredrikbogg.android_chat_app.util.SharedPreferencesUtil
import com.fredrikbogg.android_chat_app.util.convertFileToByteArray
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.log


class homeFragment : Fragment() {
    private val viewModel by viewModels<JobViewModel>()
    private lateinit var dbref: DatabaseReference
    private lateinit var talkRecyclerView: RecyclerView
    private lateinit var talkArrayList: ArrayList<Job>
    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var jobArrayList: ArrayList<Job>
    private lateinit var viewDataBinding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentHomeBinding.inflate(inflater, container, false)
            .apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setHasOptionsMenu(true)
        return viewDataBinding.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initJobRecyclerView()
    }

    private fun initJobRecyclerView(){
        val jobLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        jobRecyclerView = requireView().findViewById(R.id.rvJob)
        jobRecyclerView.layoutManager = jobLayoutManager
        jobArrayList = arrayListOf<Job>()
        jobRecyclerView.hasFixedSize()
        val talkLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        talkRecyclerView = requireView().findViewById(R.id.rvTalk)
        talkRecyclerView.layoutManager = talkLayoutManager
        talkArrayList = arrayListOf<Job>()
        talkRecyclerView.hasFixedSize()
        getData(jobRecyclerView,jobArrayList,"Job")
        getData(talkRecyclerView,talkArrayList,"Talk")
    }

    private fun getData(recyclerView: RecyclerView, arrayList: ArrayList<Job>, path:String){
        dbref = FirebaseDatabase.getInstance().getReference(path)
        dbref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(jobSnapshot in snapshot.children){
                        val job = jobSnapshot.getValue(Job::class.java)
                        arrayList.add(job!!)
                    }
                    recyclerView.adapter = JobAdaptor(arrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}