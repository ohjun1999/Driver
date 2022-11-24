package com.kingbus.driver.activity

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.databinding.ActivityWriteBinding
import com.kingbus.driver.dataclass.PostDataClass
import java.text.SimpleDateFormat
import java.util.*

class WriteActivity: AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityWriteBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityWriteBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm", Locale("ko", "KR"))
        val nowDate = dateFormat.format(date)
        val name = intent.getStringExtra("name")
        val uid = intent.getStringExtra("uid")
        binding.backKey.setOnClickListener {
            finish()
        }




        binding.endBtn.setOnClickListener {

            var postDataClass = PostDataClass()
            postDataClass.uid = uid.toString()
            postDataClass.title = binding.editTextTitle.text.toString()
            postDataClass.context = binding.editTextContent.text.toString()
            postDataClass.pubDate = nowDate.toString()
            postDataClass.comment = 0
            postDataClass.name = name.toString()
            postDataClass.view = 1
            postDataClass.pubDate = nowDate.toString()
            when(binding.postType.selectedItem.toString()){
                    "[ 자유게시판 ]" ->{
                        postDataClass.type = "자유"
                    }
                   "[ 여행게시판 ]" ->{
                       postDataClass.type = "여행"
                   }
                    "[ 공차배차게시판 ]" ->{
                        postDataClass.type = "공차배차"
                    }
                   "[ 구인구직게시판 ]" ->{
                       postDataClass.type = "구인구직"
                   }
            }



            db.collection("User").document(uid.toString())
                .update("writeCount", FieldValue.increment(1))
            db.collection("Post")
                .add(postDataClass)
                .addOnSuccessListener { documentReference ->

                    val doId = documentReference.id
                    db.collection("Post")
                        .document(doId).update("postUid", doId)
                }
                .addOnFailureListener { e ->

                }
            hideKeyboard()
            Toast.makeText(this, "글이 등록 되었습니다.", Toast.LENGTH_SHORT).show()

            finish()
        }





    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.write.windowToken, 0)
    }




}