package com.kingbus.driver.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.databinding.ActivityCarInfoBinding

class MyCarInfoActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityCarInfoBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityCarInfoBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        binding.backKey.setOnClickListener {
            finish()
        }






    }

//    fun hideKeyboard() {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(binding.write.windowToken, 0)
//    }




}