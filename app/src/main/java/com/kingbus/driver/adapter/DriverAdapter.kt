package com.kingbus.driver.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kingbus.driver.R
import com.kingbus.driver.activity.MainActivity
import com.kingbus.driver.dataclass.PostDataClass

class DriverAdapter(
    val context: Context,
    private val postList: ArrayList<PostDataClass>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // Create a reference with an initial file path and name


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as DriverAdapter.ViewHolder).itemView
        val post: PostDataClass = postList[position]

        holder.postName.text = post.name
        holder.postTitle.text = post.title
        holder.postPubDate.text = post.pubDate
        holder.countComment.visibility = View.GONE

//        if (user.email == null) {
//            holder.mailAdress.text = null
//        } else {
//            holder.mailAdress.text = user.email
//
//        }

//        if (user.files == null) {
//            Glide.with(holder.itemView)
//                .load("https://firebasestorage.googleapis.com/v0/b/seogang-firebase.appspot.com/o/files%2Fuser%2F75542fce-632a-45ce-9e29-f006587128af_%EC%9D%B4%EB%AF%B8%EC%A7%80%20%EC%97%86%EC%9D%8C.png?alt=media&token=a87135dc-7ce7-413a-b727-33f79ba1bc05")
//                .into(holder.noteImage)
//        } else {
//            Glide.with(holder.itemView)
//                .load(user.files.toString())
//                .into(holder.noteImage)
//        }


        holder.itemView.setOnClickListener {
            Toast.makeText(MainActivity(), post.type, Toast.LENGTH_SHORT).show()
//            val intent =
//                Intent(holder.itemView.context, NoteProfileDetailActivity::class.java)
//
//            intent.putExtra("content", "????????? ???????????? ????????????.")
//
//            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }

    }

    override fun getItemCount(): Int {
        return postList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val postTitle: TextView = itemView.findViewById(R.id.postTitle)
        val postName: TextView = itemView.findViewById(R.id.postName)
        val countComment: TextView = itemView.findViewById(R.id.countComment)
        val postPubDate: TextView = itemView.findViewById(R.id.postPubDate)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)


    }
}