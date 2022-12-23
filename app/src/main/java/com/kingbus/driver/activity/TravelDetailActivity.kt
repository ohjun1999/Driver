package com.kingbus.driver.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.MySharedPreferences
import com.kingbus.driver.databinding.ActivityTravelDetailBinding

class TravelDetailActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityTravelDetailBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityTravelDetailBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val title = intent.getStringExtra("title")
        val titleImg = intent.getStringExtra("titleImg")
        val pubDate = intent.getStringExtra("pubDate")
        val travelStart = intent.getStringExtra("travelStart")
        val purchaseStart = intent.getStringExtra("purchaseStart")
        val purchaseEnd = intent.getStringExtra("purchaseEnd")
        val uid = intent.getStringExtra("uid")
        val tag1 = intent.getStringExtra("tag1")
        val tag2 = intent.getStringExtra("tag2")
        val tag3 = intent.getStringExtra("tag3")
        
        db = Firebase.firestore
        auth = Firebase.auth
        binding.backKey.setOnClickListener {
            finish()
        }

        binding.topTitle.text = title + " (" + travelStart + "출발" +")"
        binding.tag1.text = "#$tag1"
        binding.tag2.text = "#$tag2"
        binding.tag3.text = "#$tag3"
        binding.mainText.text = title + " (" + travelStart + "출발" +")"
        binding.dateText.text = "$purchaseStart ~ $purchaseEnd"





    }


}