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
import com.kingbus.driver.databinding.ActivityJobDetailBinding

class JobDetailActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityJobDetailBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityJobDetailBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val uid = intent.getStringExtra("uid")
        val company = intent.getStringExtra("company")
        val companyNum = intent.getStringExtra("companyNum")
        val city = intent.getStringExtra("city")
        val context = intent.getStringExtra("context")
        val userUid = MySharedPreferences.getUserUid(this)
        db = Firebase.firestore
        auth = Firebase.auth
        binding.backKey.setOnClickListener {
            finish()
        }
        binding.submitBtn.setOnClickListener {
            db.collection("User").document(userUid)
                .update("submit", FieldValue.arrayUnion(uid))
            Toast.makeText(this, "제출이 완료 되었습니다.", Toast.LENGTH_SHORT).show()
        }



        binding.companyName.text = company
        binding.companyAdr.text = city
        binding.context.text = context
        binding.companyNum.text = companyNum


    }


}