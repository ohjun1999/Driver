package com.kingbus.driver.activity

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.MySharedPreferences
import com.kingbus.driver.databinding.ActivityPasswordChangeBinding
import java.util.*

class PasswordChangeActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityPasswordChangeBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityPasswordChangeBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        val userUid = MySharedPreferences.getUserUid(this)
        binding.backKey.setOnClickListener {
            finish()
        }

        binding.changeBtn.setOnClickListener {
            db.collection("User").whereEqualTo("uid", userUid).limit(1)
                .addSnapshotListener { documents, _ ->
                    for (document in documents!!) {

                        val password = document.getString("password")


                        if (binding.nowPassword.text.toString() == password.toString()) {
                            if (binding.newPassword.text.toString() == binding.confirmPassword.text.toString()) {
                                if (binding.newPassword.text != null && binding.newPassword.text != null) {
                                    Toast.makeText(this, "비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show()

                                }

                            } else {
                                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "기존 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show()
                        }


                    }

                }
        }


    }

//    fun hideKeyboard() {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(binding.write.windowToken, 0)
//    }


}