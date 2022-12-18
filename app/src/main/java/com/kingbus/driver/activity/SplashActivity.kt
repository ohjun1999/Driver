package com.kingbus.driver.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.MySharedPreferences
import com.kingbus.driver.databinding.ActivityPasswordChangeBinding
import com.kingbus.driver.databinding.ActivitySplashBinding
import com.kingbus.driver.dataclass.UserDataClass

class SplashActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivitySplashBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivitySplashBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        val handler = Handler()
        db = Firebase.firestore
        auth = Firebase.auth
        val userUid = MySharedPreferences.getUserUid(this)
        val go = db.collection("User").whereEqualTo("uid", userUid)
        handler.postDelayed({
            // SharedPreferences 안에 값이 저장되어 있지 않을 때 -> Login
            if (MySharedPreferences.getLogin(this).isNullOrBlank()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                go
                    .get()
                    //IF문 사용해서 빈값을 받아왔을 때 실패 메시지 document를 받아왔을 때 액티비티 이동
                    .addOnSuccessListener { documents ->

                        if (documents.size() == 0) {
                            Toast.makeText(this, "로그인 정보가 일치하지 않습니다.", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            for (document in documents) {

                                var user = UserDataClass()

                                val id = document.getString("id").toString()
                                val password = document.getString("password").toString()
                                val name = document.getString("name").toString()
                                val uid = document.getString("uid").toString()
                                val submit = document.get("submit").toString()
                                val type = document.get("type").toString()
//                        val isValidPassword = BCrypt.checkpw(inputPassword, user.password.toString())
//


                                MySharedPreferences.setUserUid(this, uid)
                                MySharedPreferences.setSubmit(this, submit)
                                MySharedPreferences.setName(this, name)
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("name", name)
                                intent.putExtra("type", type)
                                intent.putExtra("uid", uid)
                                startActivity(intent)

                            }
                        }


                    }
                    //경로가 실패했을 때
                    .addOnFailureListener { exception ->

                    }

                finish()
            }
        }, 3000)

    }

}

//    fun hideKeyboard() {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(binding.write.windowToken, 0)
//    }

