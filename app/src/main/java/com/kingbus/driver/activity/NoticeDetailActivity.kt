package com.kingbus.driver.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.databinding.ActivityPostDetailBinding

class NoticeDetailActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityPostDetailBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var noticeCommentRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        val theType = intent.getStringExtra("theType")


        db.collection("Notice").whereEqualTo("type", theType.toString()).limit(1)
            .addSnapshotListener { documents, _ ->


                for (document in documents!!) {
                    binding.postTitle.text = document.getString("title")
                    binding.postContext.text = document.getString("context")
                    binding.postPubDate.text = document.getString("pubDate")
                    binding.commentCount.text = document.get("comment").toString()
                    binding.commentCount2.text = document.get("comment").toString()
                    binding.eyesCount.text = document.get("view").toString()

                }


            }


    }
}