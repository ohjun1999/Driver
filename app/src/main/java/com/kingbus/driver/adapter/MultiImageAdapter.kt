package com.kingbus.driver.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kingbus.driver.R
import com.kingbus.driver.activity.JobDetailActivity
import com.kingbus.driver.dataclass.JobDataClass

class MultiImageAdapter(private val items: ArrayList<Uri>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        val user = items[position]



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as MultiImageAdapter.ViewHolder).itemView
        val item = items[position]
        Glide.with(context).load(item).override(500, 500).into(holder.imgOne)


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var view: View = itemView
        val imgOne: ImageView = view.findViewById(R.id.imgOne)

        fun bind(listener: View.OnClickListener, item: String) {
            view.setOnClickListener(listener)
        }

    }

    override fun getItemCount(): Int = items.size
}