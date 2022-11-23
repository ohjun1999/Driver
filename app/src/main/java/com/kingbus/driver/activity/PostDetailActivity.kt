package com.kingbus.driver.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.MySharedPreferences
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
        commentRecyclerView = binding.commentRecyclerView
        var commentList = arrayListOf<CommentDataClass>()

        if (userUid == uid){
            binding.deleteBtn.visibility = View.VISIBLE
        }
        binding.backKey.setOnClickListener {
            finish()
        }
        db.collection("Comment").whereEqualTo("postUid",postUid).addSnapshotListener { documents, _ ->
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
        binding.btnComment.setOnClickListener {
            if (binding.editTextComment.text.isNullOrBlank()){
                Toast.makeText(this, "작성된 댓글이 없습니다.", Toast.LENGTH_SHORT).show()
            }else{
                hideKeyboard()
                var commentDataClass = CommentDataClass()
                commentDataClass.name = name
                commentDataClass.context = binding.editTextComment.text.toString()
                commentDataClass.userUid = uid
                commentDataClass.postUid = postUid
                commentDataClass.pubDate = nowDate.toString()
                binding.editTextComment.setText("")

                db.collection("Comment")
                    .add(commentDataClass)
                    .addOnSuccessListener { documentReference ->

                        val doId = documentReference.id
                        db.collection("Comment")
                            .document(doId).update("uid", doId)
                    }
                    .addOnFailureListener { e ->

                    }
            }
        }



    }
    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.postDetail.windowToken, 0)
    }

}