package com.fredrikbogg.android_chat_app.ui.home


import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fredrikbogg.android_chat_app.App
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.data.Job
import com.fredrikbogg.android_chat_app.data.Talk
import com.google.firebase.database.*


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
    private lateinit var career:String
    private lateinit var careerList:List<String>
    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
//        setupViewModelObservers()
        getCareer()
        //set RecyclerView
        val jobLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        jobRecyclerView = view.findViewById(R.id.rvJob)
        jobRecyclerView.layoutManager = jobLayoutManager
        jobRecyclerView.itemAnimator?.changeDuration = 0
        jobArrayList = arrayListOf()
        jobRecyclerView.hasFixedSize()
        val talkLayoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        talkRecyclerView = view.findViewById(R.id.rvTalk)
        talkRecyclerView.layoutManager = talkLayoutManager
        talkRecyclerView.itemAnimator?.changeDuration = 0
        talkArrayList = arrayListOf()
        talkRecyclerView.hasFixedSize()
        getJobData(jobRecyclerView,jobArrayList,"Job")
        getTalkData(talkRecyclerView,talkArrayList,"Talk")

        return view
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

    private fun getCareer(){
        val dbref = FirebaseDatabase.getInstance().getReference("/users/${App.myUserID}/info/career")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                career = if(dataSnapshot.value.toString()==null){
                    null.toString()
                } else {
                    dataSnapshot.value.toString()
                }
                careerList = if(career != null)
                    career.split(" | ")
                else {
                    emptyList()
                }
                Log.e("career value", career)

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
                            if(career == "undecided")
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

//    private fun setupViewModelObservers() {
//        viewModel.userNotificationsList.observe(viewLifecycleOwner) {
//            numberOfNotification = it.size.toString()
//            Log.e("numberOfNotification",numberOfNotification)
//        }
//    }


}