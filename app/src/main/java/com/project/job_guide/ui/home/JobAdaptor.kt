package com.project.job_guide.ui.home
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.job_guide.R
import com.project.job_guide.data.Job
import com.squareup.picasso.Picasso

class JobAdaptor (
    var jobs: ArrayList<Job>
):RecyclerView.Adapter<JobAdaptor.JobViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.list_item_job,parent,false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val item = jobs[position]
        Picasso.get().load(item.profileImageUrl).into(holder.image)
        holder.title.text = item.title
        holder.deadline.text = "Deadline: " + item.deadline
        holder.salary.text ="Salary: " + item.salary
        holder.link.text ="Link: " + item.link
        val isExpandable :Boolean = jobs[position].expandable
        holder.linearLayout.visibility = if(isExpandable) View.VISIBLE else View.GONE
        holder.expandView.setBackgroundResource(if(isExpandable) R.drawable.ic_baseline_expand_less_24 else
            R.drawable.ic_baseline_expand_more_24)

        holder.expandView.setOnClickListener{
            val card = jobs[position]
            card.expandable = !card.expandable
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return jobs.size
    }
    class JobViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.info_text)
        var image: ImageView = itemView.findViewById(R.id.event_image)
        var deadline : TextView = itemView.findViewById(R.id.job_deadline)
        var salary : TextView = itemView.findViewById(R.id.job_Salary)
        var link : TextView = itemView.findViewById(R.id.job_Link)
        val linearLayout:View = itemView.findViewById(R.id.expandableLayout)
        val cardView:View = itemView.findViewById(R.id.card_view)
        var expandView: View = itemView.findViewById(R.id.expand_button)

    }
}