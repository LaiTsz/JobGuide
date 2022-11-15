package com.fredrikbogg.android_chat_app.ui.home
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.data.Talk_Job

class EventAdapter (
    var talkJobs: List<Talk_Job>
):RecyclerView.Adapter<EventAdapter.TodoViewHolder>(){
    inner class TodoViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.info_text)
        val eventImage: ImageView = itemView.findViewById(R.id.event_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.list_item_talk_job,parent,false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = talkJobs[position]
        holder.eventImage.setImageResource(item.image)
        holder.title.text = item.title
    }

    override fun getItemCount(): Int {
        return talkJobs.size
    }
}