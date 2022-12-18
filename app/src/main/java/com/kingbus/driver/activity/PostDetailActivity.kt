package com.kingbus.driver.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.MySharedPreferences
import com.kingbus.driver.R
import com.kingbus.driver.adapter.CommentAdapter
import com.kingbus.driver.adapter.PostAdapter
import com.kingbus.driver.databinding.ActivityPostDetailBinding
import com.kingbus.driver.dataclass.CommentDataClass
import com.kingbus.driver.dataclass.PostDataClass
import com.kingbus.driver.dataclass.UserDataClass
import java.text.SimpleDateFormat
import java.util.*

class PostDetailActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityPostDetailBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var commentRecyclerView: RecyclerView
    lateinit var albumImg: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // binding class 인스턴스 생성
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm", Locale("ko", "KR"))
        val nowDate = dateFormat.format(date)
        val title = intent.getStringExtra("title")
        val name = intent.getStringExtra("name")
        val pubDate = intent.getStringExtra("pubDate")
        val comment = intent.getStringExtra("comment")
        val uid = intent.getStringExtra("uid")
        val context = intent.getStringExtra("context")
        val view2 = intent.getStringExtra("view")
        val img = intent.getStringExtra("img")
        val type = intent.getStringExtra("type")
        val postUid = intent.getStringExtra("postUid")
        binding.commentCount.text = comment
        binding.commentCount2.text = comment
        binding.eyesCount.text = view2
        binding.postPubDate.text = pubDate
        binding.postContext.text = context
        binding.postName.text = name
        binding.postTitle.text = title
        val userUid = MySharedPreferences.getUserUid(this)
        val userName = MySharedPreferences.getName(this)
        commentRecyclerView = binding.commentRecyclerView
        var commentList = arrayListOf<CommentDataClass>()

        if (userUid == uid) {
            binding.deleteBtn.visibility = View.VISIBLE
        }
        binding.backKey.setOnClickListener {
            finish()
        }
        albumImg = binding.albumImg

        var albumImgList = arrayListOf<String>()
        db.collection("Post").whereEqualTo("postUid", postUid)
            .get().addOnSuccessListener { documents ->


                for (document in documents) {
                    if (document.get("imgLink") == null) {
                        binding.postImg.visibility = View.VISIBLE
                        binding.albumImg.visibility =View.GONE
                    } else {
                        albumImgList = document.get("imgLink") as ArrayList<String>
                    }


                }
                val realNoticeAdapter = RealNoticeAdapter(this, albumImgList)
                albumImg.adapter = realNoticeAdapter
                albumImg.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                Log.d("test12", albumImgList.toString())


            }

        db.collection("Comment").whereEqualTo("postUid", postUid)
            .addSnapshotListener { documents, _ ->
                commentList.clear()
                for (document in documents!!) {
                    Log.d(document.id, document.data.toString())
                    var item = document.toObject(CommentDataClass::class.java)
                    commentList.add(item)
                }
                val commentAdapter =
                    CommentAdapter(PostDetailActivity(), commentList)
                commentRecyclerView.adapter = commentAdapter
                commentRecyclerView.layoutManager =
                    LinearLayoutManager(MainActivity(), RecyclerView.VERTICAL, false)


            }
        binding.deleteBtn.setOnClickListener {

            db.collection("Post").whereEqualTo("postUid", postUid)
                .addSnapshotListener { documents, _ ->

                    for (document in documents!!) {
                        var commentUid: ArrayList<Any>? = ArrayList()
                        commentUid = document.get("commentList") as ArrayList<Any>?
                        if (commentUid!!.size == 0) {

                        } else {
                            for (i in commentUid) {
                                db.collection("Comment").document(i.toString()).delete()
                                    .addOnSuccessListener {

                                    }
                            }

                        }
                    }

                }


            db.collection("User").document(userUid).update("writeCount", FieldValue.increment(-1))
//            db.collection("User").document(userUid)
//                .update("submit", FieldValue.arrayRemove(uid))
            db.collection("Post").document(postUid.toString()).delete().addOnSuccessListener {


            }

            finish()
        }
        binding.btnComment.setOnClickListener {
            if (binding.editTextComment.text.isNullOrBlank()) {
                Toast.makeText(this, "작성된 댓글이 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                hideKeyboard()
                var commentDataClass = CommentDataClass()
                commentDataClass.name = userName
                commentDataClass.context = binding.editTextComment.text.toString()
                commentDataClass.userUid = userUid
                commentDataClass.postUid = postUid
                commentDataClass.pubDate = nowDate.toString()
                binding.editTextComment.setText("")

                db.collection("Comment")
                    .add(commentDataClass)
                    .addOnSuccessListener { documentReference ->

                        val doId = documentReference.id
                        db.collection("Comment")
                            .document(doId).update("uid", doId)
                        db.collection("Post")
                            .document(postUid.toString())
                            .update("commentList", FieldValue.arrayUnion(doId))

                    }
                    .addOnFailureListener { e ->

                    }
            }
        }


    }

    inner class RealNoticeAdapter(
        val context: Context,
        var albumImgList: ArrayList<String>,

        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var firestore: FirebaseFirestore? = null
        var db = Firebase.firestore
        lateinit var auth: FirebaseAuth


        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_notice_img, parent, false)
            val olcYear = albumImgList[position]



            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as RealNoticeAdapter.ViewHolder).itemView
            val notice = albumImgList[position]


            Glide.with(holder.itemView)
                .load(notice)
                .into(holder.albumRealImg)


        }

        override fun getItemCount(): Int {
            return albumImgList.size
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val albumRealImg: ImageView = itemView.findViewById(R.id.albumRealImg)


        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.postDetail.windowToken, 0)
    }

}