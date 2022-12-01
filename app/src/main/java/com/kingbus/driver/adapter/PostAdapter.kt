package com.kingbus.driver.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.R
import com.kingbus.driver.activity.MainActivity
import com.kingbus.driver.activity.PostDetailActivity
import com.kingbus.driver.dataclass.PostDataClass
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PostAdapter(
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
        var viewHolder = (holder as PostAdapter.ViewHolder).itemView
        val post: PostDataClass = postList[position]
        var db = Firebase.firestore
        var auth: FirebaseAuth = Firebase.auth
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm", Locale("ko", "KR"))
        val nowDate = dateFormat.format(date)

        if (post.pubDate.toString().substring(0,10) == nowDate.toString().substring(0,10)){
            holder.postPubDate.text = post.pubDate.toString().substring(11,16)
        }else{
            holder.postPubDate.text =   post.pubDate.toString().substring(0,10)
        }

        holder.postName.text = post.name
        holder.postTitle.text = post.title
//        holder.postPubDate.text = post.pubDate

        if (post.commentList?.size == 0){
            holder.countComment.text ="댓글 " + "0"
        }else{
            holder.countComment.text = "댓글 " + post.commentList?.size.toString()
        }
        holder.countView.text = "조회 " + post.view.toString()


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
//            Toast.makeText(MainActivity(), post.type, Toast.LENGTH_SHORT).show()
            val intent =
                Intent(holder.itemView.context, PostDetailActivity::class.java)

            intent.putExtra("title", post.title)
            intent.putExtra("name", post.name)
            intent.putExtra("pubDate", post.pubDate)
            intent.putExtra("comment", post.commentList?.size.toString())
            intent.putExtra("uid", post.uid)
            intent.putExtra("context", post.context)
            intent.putExtra("view", post.view.toString())
            intent.putExtra("img", post.img)
            intent.putExtra("type", post.type)
            intent.putExtra("postUid", post.postUid)
            db.collection("Post")
                .document(post.postUid.toString()).update("view", FieldValue.increment(1))

            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }

    }

    override fun getItemCount(): Int {
        return postList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val postTitle: TextView = itemView.findViewById(R.id.postTitle)
        val postName: TextView = itemView.findViewById(R.id.postName)
        val countComment: TextView = itemView.findViewById(R.id.countComment)
        val countView: TextView = itemView.findViewById(R.id.countView)
        val postPubDate: TextView = itemView.findViewById(R.id.postPubDate)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)


    }
}