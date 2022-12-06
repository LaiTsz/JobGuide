package com.fredrikbogg.android_chat_app.ui.home


import android.os.Bundle
import android.text.TextUtils.split
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fredrikbogg.android_chat_app.App
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.data.Job
import com.fredrikbogg.android_chat_app.data.Talk
import com.fredrikbogg.android_chat_app.databinding.FragmentHomeBinding
import com.fredrikbogg.android_chat_app.ui.post.PostFragment
import com.fredrikbogg.android_chat_app.ui.post.PostViewModel
import com.fredrikbogg.android_chat_app.ui.post.PostViewModelFactory
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*


class homeFragment : Fragment() {
    private val viewModel: JobViewModel by viewModels {
        JobViewModelFactory(
           App.myUserID
        )
    }
    private lateinit var dbref: DatabaseReference
    private lateinit var talkRecyclerView: RecyclerView
    private lateinit var talkArrayList: ArrayList<Talk>
    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var jobArrayList: ArrayList<Job>
    private lateinit var viewDataBinding: FragmentHomeBinding
    private lateinit var career:String
    private lateinit var careerList:List<String>
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
        jobRecyclerView.itemAnimator?.changeDuration = 0
        jobArrayList = arrayListOf()
        jobRecyclerView.hasFixedSize()
        val talkLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        talkRecyclerView = requireView().findViewById(R.id.rvTalk)
        talkRecyclerView.layoutManager = talkLayoutManager
        talkRecyclerView.itemAnimator?.changeDuration = 0
        talkArrayList = arrayListOf()
        talkRecyclerView.hasFixedSize()
        getCareer()
        getJobData(jobRecyclerView,jobArrayList,"Job")
        getTalkData(talkRecyclerView,talkArrayList,"Talk")
    }


    private fun getCareer(){
        Log.e("career value", "enter the get career")
        val dbref = FirebaseDatabase.getInstance().getReference("/users/${App.myUserID}/info/career")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                career = dataSnapshot.value.toString()
                careerList = career.split(" ")

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.e("career value", "cannot load a career")
            }
        })
    }
    private fun getTalkData(recyclerView: RecyclerView, arrayList: ArrayList<Talk>, path:String){
        dbref = FirebaseDatabase.getInstance().getReference(path)
        dbref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(jobSnapshot in snapshot.children){
                        Log.e("snapshot testing",jobSnapshot.toString())
                        val talk = jobSnapshot.getValue(Talk::class.java)
                        arrayList.add(talk!!)
                    }
                    val adapter = TalkAdaptor(arrayList)
                    recyclerView.adapter = adapter

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    private fun getJobData(recyclerView: RecyclerView, arrayList: ArrayList<Job>, path:String){
        dbref = FirebaseDatabase.getInstance().getReference(path)
        dbref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(jobSnapshot in snapshot.children){
                        val job = jobSnapshot.getValue(Job::class.java)
                        if (job != null) {
                            if(job.career ==null)
                                arrayList.add(job!!)
                            else{
                                for (jobCareer in careerList)
                                {
                                    if (job.career == jobCareer)
                                    {arrayList.add(job!!)}
                                }
                            }
                        }
                    }
                    val adapter = JobAdaptor(arrayList)
                    recyclerView.adapter = adapter

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}