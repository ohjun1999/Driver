package com.kingbus.driver.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kingbus.driver.R
import com.kingbus.driver.dataclass.CommentDataClass
import com.kingbus.driver.dataclass.ReviewDataClass

class ReviewAdapter (
    val context: Context,
    private val reviewList: ArrayList<ReviewDataClass>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // Create a reference with an initial file path and name


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as ReviewAdapter.ViewHolder).itemView
        val comment: ReviewDataClass = reviewList[position]
        holder.commentContext.text = comment.context
        holder.commentName.text = comment.name
        holder.commentPubDate.text = comment.pubDate


    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val commentContext: TextView = itemView.findViewById(R.id.commentContext)
        val commentName: TextView = itemView.findViewById(R.id.commentName)
        val commentPubDate: TextView = itemView.findViewById(R.id.commentPubDate)
//        val deleteBtn: AppCompatButton = itemView.findViewById(R.id.deleteBtn)


    }
}