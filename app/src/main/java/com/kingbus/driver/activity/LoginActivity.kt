package com.kingbus.driver.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.MySharedPreferences
import com.kingbus.driver.databinding.ActivityLoginBinding
import com.kingbus.driver.dataclass.UserDataClass

class LoginActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityLoginBinding
    private var doubleBackToExit = false
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityLoginBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        binding.loginBtn.setOnClickListener {

            val inputPassword = binding.passwordText.text.toString()
            val inputId = binding.idText.text.toString()
            val login = db.collection("User").whereEqualTo("id", inputId)
            when {
                inputId.isEmpty() -> {
                    Toast.makeText(this, "아이디가 빈칸입니다.", Toast.LENGTH_SHORT)
                        .show()
                }
                inputPassword.isEmpty() -> {
                    Toast.makeText(this, "비밀번호가 빈칸입니다.", Toast.LENGTH_SHORT)
                        .show()
                }
                else ->
                    login
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
                                    val province = document.get("province").toString()
                                    val city = document.get("city").toString()
                                    val type = document.get("type").toString()
//                        val isValidPassword = BCrypt.checkpw(inputPassword, user.password.toString())
//


                                    if (id == inputId && password == inputPassword) {

                                        MySharedPreferences.setUserUid(this, uid)
                                        MySharedPreferences.setSubmit(this, submit)
                                        MySharedPreferences.setName(this, name)
                                        if (binding.maintainCheck.isChecked) {
                                            MySharedPreferences.setLogin(this, "check")
                                            val intent = Intent(this, MainActivity::class.java)
                                            intent.putExtra("name", name)
                                            intent.putExtra("uid", uid)
                                            intent.putExtra("type", type)
                                            intent.putExtra("province", province)
                                            intent.putExtra("city", city)
                                            startActivity(intent)
                                            finish()
                                        } else {
                                            val intent = Intent(this, MainActivity::class.java)
                                            intent.putExtra("name", name)
                                            intent.putExtra("uid", uid)
                                            intent.putExtra("type", type)
                                            intent.putExtra("province", province)
                                            intent.putExtra("city", city)
                                            startActivity(intent)
                                            finish()
                                        }

                                    } else {

                                    }


                                }
                            }


                        }
                        //경로가 실패했을 때
                        .addOnFailureListener { exception ->
                            Toast.makeText(this, "등록되지 않은 번호입니다.", Toast.LENGTH_SHORT).show()
                        }
            }
        }

        binding.accountBtn.setOnClickListener {
            val intent = Intent(this, AuthorityAgreeActivity::class.java)

            startActivity(intent)
        }


    }

    override fun onBackPressed() {
        if (doubleBackToExit) {
            finishAffinity()
        } else {
            Toast.makeText(this, "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
            doubleBackToExit = true
            runDelayed(1500L) {
                doubleBackToExit = false
            }
        }
    }


    fun runDelayed(millis: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }

}