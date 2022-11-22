package com.kingbus.driver.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.databinding.ActivityAccountBinding
import com.kingbus.driver.databinding.ActivityWriteBinding

class AccountActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityAccountBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityAccountBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth



//        binding.endBtn.setOnClickListener {
//
//            var postDataClass = PostDataClass()
//            postDataClass.uid = id.toString()
//            postDataClass.title = binding.editTextTitle.text.toString()
//            postDataClass.context = binding.editTextContent.text.toString()
//            postDataClass.pubDate = nowDate.toString()
//            postDataClass.creator = name.toString()
//            postDataClass.modifiedDate = nowDate.toString()
//            postDataClass.check = "X"
//
//
////            db.collection("Counter").document("counter")
////                .update("question", FieldValue.increment(1))
//            db.collection("Post")
//                .add(postDataClass)
//                .addOnSuccessListener { documentReference ->
//
//                    val doId = documentReference.id
//                    db.collection("Post")
//                        .document(doId).update("uuid", doId)
//                }
//                .addOnFailureListener { e ->
//
//                }
//            hideKeyboard()
//            Toast.makeText(this, "문의가 접수 되었습니다", Toast.LENGTH_SHORT).show()
//
//            finish()
//        }





    }






}