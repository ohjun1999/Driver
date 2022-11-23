package com.kingbus.driver.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.databinding.ActivityAccountBinding
import com.kingbus.driver.databinding.ActivityWriteBinding
import com.kingbus.driver.dataclass.UserDataClass

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
        //파이어베이스 초기화

        binding.btnMale.isChecked
        // 성별 라디오 버튼 체크설정


        var userType = "전세버스"
        var cityType = "서울"


        val companyNameL = binding.companyNameL
        val birthL = binding.birthL
        val sectorTypeL = binding.sectorTypeL
        val genderL = binding.genderL
        val companyAdrL = binding.companyAdrL
        //visibility 보여주는 것과 숨기는것을 위한 linearLayout 정의

        Log.d("test", userType)
        binding.userType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (binding.userType.getItemAtPosition(position)) {
                    "전세버스" -> {
                        userType = "전세버스"
                        companyNameL.visibility = View.VISIBLE
                        birthL.visibility = View.GONE
                        sectorTypeL.visibility = View.GONE
                        genderL.visibility = View.GONE
                        companyAdrL.visibility = View.GONE

                        binding.authBtn.setOnClickListener {
                            if (binding.nameInfo.text.isNullOrBlank() || binding.idInfo.text.isNullOrBlank() || binding.passwordInfo.text.isNullOrBlank() || binding.companyName.text.isNullOrBlank() || binding.phoneNum.text.isNullOrBlank()) {
                                Toast.makeText(
                                    this@AccountActivity,
                                    "모든 정보가 입력 되지 않았습니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            } else {
                                var userDataClass = UserDataClass()
                                userDataClass.name = binding.nameInfo.text.toString()
                                userDataClass.loginCheck = "X"
                                userDataClass.id = binding.idInfo.text.toString()
                                userDataClass.password = binding.passwordInfo.text.toString()
                                userDataClass.type = userType
                                userDataClass.province = cityType
                                userDataClass.phoneNum = binding.phoneNum.text.toString()
                                if (cityType == "서울") {
                                    userDataClass.city = binding.seoul.selectedItem.toString()
                                } else if (cityType == "경기") {
                                    userDataClass.city = binding.gyeonggi.selectedItem.toString()
                                }
                                userDataClass.company = binding.companyName.text.toString()


                                db.collection("User")
                                    .add(userDataClass)
                                    .addOnSuccessListener { documentReference ->

                                        val doId = documentReference.id
                                        db.collection("User")
                                            .document(doId).update("uid", doId)
                                    }
                                    .addOnFailureListener { e ->

                                    }
                                Toast.makeText(
                                    this@AccountActivity,
                                    "회원가입이 완료 되었습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                finish()
                            }
                        }

                    }
                    "일반회원" -> {
                        userType = "일반회원"
                        companyNameL.visibility = View.GONE
                        birthL.visibility = View.VISIBLE
                        sectorTypeL.visibility = View.GONE
                        genderL.visibility = View.VISIBLE
                        companyAdrL.visibility = View.GONE
                        binding.authBtn.setOnClickListener {
                            if (binding.nameInfo.text.isNullOrBlank() || binding.idInfo.text.isNullOrBlank() || binding.passwordInfo.text.isNullOrBlank() || binding.phoneNum.text.isNullOrBlank()) {
                                Toast.makeText(
                                    this@AccountActivity,
                                    "모든 정보가 입력 되지 않았습니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            } else {
                                var userDataClass = UserDataClass()
                                userDataClass.name = binding.nameInfo.text.toString()
                                userDataClass.loginCheck = "X"
                                userDataClass.id = binding.idInfo.text.toString()
                                userDataClass.password = binding.passwordInfo.text.toString()
                                userDataClass.type = userType
                                userDataClass.province = cityType
                                userDataClass.phoneNum = binding.phoneNum.text.toString()
                                if (cityType == "서울") {
                                    userDataClass.city = binding.seoul.selectedItem.toString()
                                } else if (cityType == "경기") {
                                    userDataClass.city = binding.gyeonggi.selectedItem.toString()
                                }
                                if (binding.btnMale.isChecked) {
                                    userDataClass.gender = "남성"
                                } else if (binding.btnFemale.isChecked) {
                                    userDataClass.gender = "여성"
                                }
                                userDataClass.birth =
                                    binding.year.selectedItem.toString() + "-" + binding.month.selectedItem.toString() + "-" + binding.date.selectedItem.toString()


                                db.collection("User")
                                    .add(userDataClass)
                                    .addOnSuccessListener { documentReference ->

                                        val doId = documentReference.id
                                        db.collection("User")
                                            .document(doId).update("uid", doId)
                                    }
                                    .addOnFailureListener { e ->

                                    }
                                Toast.makeText(
                                    this@AccountActivity,
                                    "회원가입이 완료 되었습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                finish()
                            }
                        }

                    }
                    "제휴업체" -> {
                        userType = "제휴업체"
                        companyNameL.visibility = View.VISIBLE
                        birthL.visibility = View.GONE
                        sectorTypeL.visibility = View.VISIBLE
                        genderL.visibility = View.GONE
                        companyAdrL.visibility = View.VISIBLE
                        binding.authBtn.setOnClickListener {
                            if (binding.nameInfo.text.isNullOrBlank() || binding.idInfo.text.isNullOrBlank() || binding.passwordInfo.text.isNullOrBlank() || binding.companyName.text.isNullOrBlank() || binding.phoneNum.text.isNullOrBlank() || binding.companyAdr.text.isNullOrBlank()) {
                                Toast.makeText(
                                    this@AccountActivity,
                                    "모든 정보가 입력 되지 않았습니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            } else {
                                var userDataClass = UserDataClass()
                                userDataClass.name = binding.nameInfo.text.toString()
                                userDataClass.loginCheck = "X"
                                userDataClass.id = binding.idInfo.text.toString()
                                userDataClass.password = binding.passwordInfo.text.toString()
                                userDataClass.type = userType
                                userDataClass.province = cityType
                                userDataClass.phoneNum = binding.phoneNum.text.toString()
                                if (cityType == "서울") {
                                    userDataClass.city = binding.seoul.selectedItem.toString()
                                } else if (cityType == "경기") {
                                    userDataClass.city = binding.gyeonggi.selectedItem.toString()
                                }
                                userDataClass.company = binding.companyName.text.toString()
                                userDataClass.companyAdr = binding.companyAdr.text.toString()
                                userDataClass.sector = binding.sectorType.selectedItem.toString()

                                db.collection("User")
                                    .add(userDataClass)
                                    .addOnSuccessListener { documentReference ->

                                        val doId = documentReference.id
                                        db.collection("User")
                                            .document(doId).update("uid", doId)
                                    }
                                    .addOnFailureListener { e ->

                                    }
                                Toast.makeText(
                                    this@AccountActivity,
                                    "회원가입이 완료 되었습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                finish()
                            }
                        }
                    }
                }
            }
        }
        //회원 구분에 따른 하단의 정보 요청들 보일지 안보일지

        binding.province.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (binding.province.getItemAtPosition(position)) {
                    "서울" -> {
                        cityType = "서울"
                        binding.gyeonggi.visibility = View.GONE

                    }
                    "경기" -> {
                        cityType = "경기"
                        binding.gyeonggi.visibility = View.VISIBLE
                    }

                }
            }
        }
        // 서울 경기 스피너 선택에 따른 이차 선택지 변경


    }


}