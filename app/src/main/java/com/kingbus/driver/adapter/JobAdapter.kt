package com.kingbus.driver.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kingbus.driver.R
import com.kingbus.driver.activity.JobDetailActivity
import com.kingbus.driver.dataclass.JobDataClass

class JobAdapter(
    val jobArray: String,
    val iduser: String,
    val context: Context,
    val jobList: ArrayList<JobDataClass>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val deNote: ArrayList<JobDataClass> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        val user = jobList[position]



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as ViewHolder).itemView
        val job: JobDataClass = jobList[position]

        holder.companyName.text = job.company
        holder.possible.text = job.possible
        holder.context.text = job.context
        holder.old.text = job.startOld + "~" + job.endOld + "세"
        holder.city.text = job.city

        if (jobArray.contains(job.uid.toString())){
            holder.possible.text = "제출 완료"
        }else{
            holder.possible.text = "등록 가능"
        }


        holder.endDate.text = job.endDate + "까지"
        holder.itemView.setOnClickListener {

            val intent =
                Intent(holder.itemView.context, JobDetailActivity::class.java)
            intent.putExtra("uid", job.uid)
            intent.putExtra("company", job.company)
            intent.putExtra("companyNum", job.companyNum)
            intent.putExtra("city", job.city)
            intent.putExtra("context", job.context)

            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }

    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val companyName: TextView = itemView.findViewById(R.id.companyName)
        val possible: TextView = itemView.findViewById(R.id.possible)
        val context: TextView = itemView.findViewById(R.id.context)
        val endDate: TextView = itemView.findViewById(R.id.endDate)
        val old: TextView = itemView.findViewById(R.id.old)
        val city: TextView = itemView.findViewById(R.id.city)
    }
}