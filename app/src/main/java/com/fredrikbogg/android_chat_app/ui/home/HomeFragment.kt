package com.fredrikbogg.android_chat_app.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.data.Talk_Job
import kotlinx.android.synthetic.main.fragment_home.*


class homeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }
    fun initRecyclerView(){
        val eventBackgroundList=mutableListOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8
        )
        val talkList = mutableListOf(
            Talk_Job("asdfssdf",eventBackgroundList[0]),
            Talk_Job("asdfssdf",eventBackgroundList[1]),
            Talk_Job("asdfssdf",eventBackgroundList[2]),
            Talk_Job("asdfssdf",eventBackgroundList[3]),
            Talk_Job("asdfssdf",eventBackgroundList[4]),
            Talk_Job("asdfssdf",eventBackgroundList[5]),
            Talk_Job("asdfssdf",eventBackgroundList[6]),
            Talk_Job("asdfssdf",eventBackgroundList[7])
        )
        val jobList = mutableListOf(
            Talk_Job("sasfasbs",eventBackgroundList[0]),
            Talk_Job("sasfasbs",eventBackgroundList[1]),
            Talk_Job("sasfasbs",eventBackgroundList[2]),
            Talk_Job("sasfasbs",eventBackgroundList[3]),
            Talk_Job("sasfasbs",eventBackgroundList[4]),
            Talk_Job("sasfasbs",eventBackgroundList[5]),
            Talk_Job("sasfasbs",eventBackgroundList[6]),
            Talk_Job("sasfasbs",eventBackgroundList[7])
        )
        val rvTalk : RecyclerView? = view?.findViewById(R.id.rvTalk)
        val rvJob : RecyclerView? = view?.findViewById(R.id.rvJob)
        val talkAdapter = EventAdapter(talkList)
        val jobAdapter = EventAdapter(jobList)
        Log.d("homeFragment", "test")
        if (rvTalk != null && rvJob !=null) {
            rvTalk.adapter = talkAdapter
            rvJob.adapter = jobAdapter
        }
        if (rvTalk != null && rvJob !=null) {
            rvTalk.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
            rvJob.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
        }
        val btnAddTodo : Button? = view?.findViewById(R.id.btnAddTodo)
        if (btnAddTodo != null) {
            btnAddTodo.setOnClickListener {
                val title = etTodo.text.toString()
                val event = Talk_Job(title,eventBackgroundList[0])
                talkList.add(event)
                talkAdapter.notifyItemInserted(talkList.size - 1)
            }
        }
    }
}