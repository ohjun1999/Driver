package com.kingbus.driver.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.databinding.ActivityPostDetailBinding

class PostDetailActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityPostDetailBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // binding class 인스턴스 생성
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth

        val title = intent.getStringExtra("title")
        val name = intent.getStringExtra("name")
        val pubDate = intent.getStringExtra("pubDate")
        val comment = intent.getStringExtra("comment")
        val uid = intent.getStringExtra("uid")
        val context = intent.getStringExtra("context")
        val view2 = intent.getStringExtra("view")
        val img = intent.getStringExtra("img")
        val type = intent.getStringExtra("type")

        binding.commentCount.text = comment
        binding.commentCount2.text = comment
        binding.eyesCount.text = view2
        binding.postPubDate.text = pubDate
        binding.postContext.text = context
        binding.postName.text = name
        binding.postTitle.text = title

        binding.backKey.setOnClickListener {
            finish()
        }


    }


}