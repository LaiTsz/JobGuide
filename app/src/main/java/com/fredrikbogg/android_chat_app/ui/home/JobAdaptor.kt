package com.fredrikbogg.android_chat_app.ui.home
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fredrikbogg.android_chat_app.R
import com.fredrikbogg.android_chat_app.data.Job
import com.fredrikbogg.android_chat_app.ui.bindImageWithPicasso
import com.squareup.picasso.Picasso

class JobAdaptor (
    var talkJobs: ArrayList<Job>
):RecyclerView.Adapter<JobAdaptor.JobViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.list_item_talk_job,parent,false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val item = talkJobs[position]
        Picasso.get().load(item.profileImageUrl).into(holder.image)
        holder.title.text = item.title
    }

    override fun getItemCount(): Int {
        return talkJobs.size
    }
    class JobViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.info_text)

        var image: ImageView = itemView.findViewById(R.id.event_image)
    }
}