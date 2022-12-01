package com.kingbus.driver.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.kingbus.driver.MySharedPreferences
import com.kingbus.driver.databinding.ActivityAccountBinding
import com.kingbus.driver.dataclass.UserDataClass
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AccountActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityAccountBinding
    var verificationId = ""
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    private var viewProfile: View? = null
    var pickImageFromAlbum = 0
    var fbStorage: FirebaseStorage? = null
    var uriPhoto: Uri? = null


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
        viewProfile = binding.imgBtn


        fbStorage = FirebaseStorage.getInstance()
        var userType = "전세버스"
        var cityType = "서울"

        binding.main.setOnClickListener {
            hideKeyboard()
        }

        binding.imgBtn.setOnClickListener {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
            var photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, pickImageFromAlbum)
        }


        val companyNameL = binding.companyNameL
        val birthL = binding.birthL
        val sectorTypeL = binding.sectorTypeL
        val genderL = binding.genderL
        val companyAdrL = binding.companyAdrL
        //visibility 보여주는 것과 숨기는것을 위한 linearLayout 정의

        Log.d("test", userType)

        var checkId = "O"

        binding.idCheck.setOnClickListener {
            db.collection("User").whereEqualTo("id", binding.idInfo.text.toString()).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        if (document.exists()) {
                            checkId = "X"
                            Toast.makeText(this, "사용하고 있는 아이디 입니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            checkId = "O"
                            Toast.makeText(this, "사용 가능한 아이디 입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

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

                            } else if (checkId == "X") {
                                Toast.makeText(
                                    this@AccountActivity,
                                    "이미 사용하고 있는 아이디입니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else if (binding.passwordInfo.text.toString() != binding.passwordConfirm.text.toString()) {
                                Toast.makeText(
                                    this@AccountActivity,
                                    "비밀번호가 일치하지 않습니다.",
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
                                userDataClass.writeCount = 0
                                userDataClass.province = cityType
                                userDataClass.phoneNum = binding.phoneNum.text.toString()

                                funImageUpload(viewProfile!!)
                                userDataClass.imageUri = MySharedPreferences.getImage(this@AccountActivity)



                                if (cityType == "서울시") {
                                    userDataClass.city = binding.seoul.selectedItem.toString()
                                } else if (cityType == "경기도") {
                                    userDataClass.city = binding.gyeonggi.selectedItem.toString()
                                } else if (cityType == "인천시") {
                                    userDataClass.city = binding.incheon.selectedItem.toString()
                                } else if (cityType == "충청북도") {
                                    userDataClass.city =
                                        binding.chungcheongbuk.selectedItem.toString()
                                } else if (cityType == "충청남도") {
                                    userDataClass.city =
                                        binding.chungcheongnam.selectedItem.toString()
                                } else if (cityType == "강원도") {
                                    userDataClass.city = binding.gangwon.selectedItem.toString()
                                } else if (cityType == "경상북도") {
                                    userDataClass.city =
                                        binding.gyeongsangbuk.selectedItem.toString()
                                } else if (cityType == "경상남도") {
                                    userDataClass.city =
                                        binding.gyeongsangnam.selectedItem.toString()
                                } else if (cityType == "전라북도") {
                                    userDataClass.city = binding.jeollabuk.selectedItem.toString()
                                } else if (cityType == "전라남도") {
                                    userDataClass.city = binding.jeollanam.selectedItem.toString()
                                } else if (cityType == "제주도") {
                                    userDataClass.city = binding.jeju.selectedItem.toString()
                                } else if (cityType == "부산시") {
                                    userDataClass.city = binding.busan.selectedItem.toString()
                                } else if (cityType == "광주시") {
                                    userDataClass.city = binding.gwangju.selectedItem.toString()
                                } else if (cityType == "대구시") {
                                    userDataClass.city = binding.daegu.selectedItem.toString()
                                } else if (cityType == "대전시") {
                                    userDataClass.city = binding.daejeon.selectedItem.toString()
                                } else if (cityType == "세종시") {
                                    userDataClass.city = binding.sejong.selectedItem.toString()
                                } else if (cityType == "울산시") {
                                    userDataClass.city = binding.ulsan.selectedItem.toString()
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

                            } else if (checkId == "X") {
                                Toast.makeText(
                                    this@AccountActivity,
                                    "이미 사용하고 있는 아이디입니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else if (binding.passwordInfo.text.toString() != binding.passwordConfirm.text.toString()) {
                                Toast.makeText(
                                    this@AccountActivity,
                                    "비밀번호가 일치하지 않습니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else {


                                var userDataClass = UserDataClass()
                                funImageUpload(viewProfile!!)
                                userDataClass.imageUri = MySharedPreferences.getImage(this@AccountActivity)
                                userDataClass.name = binding.nameInfo.text.toString()
                                userDataClass.loginCheck = "X"
                                userDataClass.writeCount = 0
                                userDataClass.id = binding.idInfo.text.toString()
                                userDataClass.password = binding.passwordInfo.text.toString()
                                userDataClass.type = userType
                                userDataClass.province = cityType
                                userDataClass.phoneNum = binding.phoneNum.text.toString()
                                if (cityType == "서울시") {
                                    userDataClass.city = binding.seoul.selectedItem.toString()
                                } else if (cityType == "경기도") {
                                    userDataClass.city = binding.gyeonggi.selectedItem.toString()
                                } else if (cityType == "인천시") {
                                    userDataClass.city = binding.incheon.selectedItem.toString()
                                } else if (cityType == "충청북도") {
                                    userDataClass.city =
                                        binding.chungcheongbuk.selectedItem.toString()
                                } else if (cityType == "충청남도") {
                                    userDataClass.city =
                                        binding.chungcheongnam.selectedItem.toString()
                                } else if (cityType == "강원도") {
                                    userDataClass.city = binding.gangwon.selectedItem.toString()
                                } else if (cityType == "경상북도") {
                                    userDataClass.city =
                                        binding.gyeongsangbuk.selectedItem.toString()
                                } else if (cityType == "경상남도") {
                                    userDataClass.city =
                                        binding.gyeongsangnam.selectedItem.toString()
                                } else if (cityType == "전라북도") {
                                    userDataClass.city = binding.jeollabuk.selectedItem.toString()
                                } else if (cityType == "전라남도") {
                                    userDataClass.city = binding.jeollanam.selectedItem.toString()
                                } else if (cityType == "제주도") {
                                    userDataClass.city = binding.jeju.selectedItem.toString()
                                } else if (cityType == "부산시") {
                                    userDataClass.city = binding.busan.selectedItem.toString()
                                } else if (cityType == "광주시") {
                                    userDataClass.city = binding.gwangju.selectedItem.toString()
                                } else if (cityType == "대구시") {
                                    userDataClass.city = binding.daegu.selectedItem.toString()
                                } else if (cityType == "대전시") {
                                    userDataClass.city = binding.daejeon.selectedItem.toString()
                                } else if (cityType == "세종시") {
                                    userDataClass.city = binding.sejong.selectedItem.toString()
                                } else if (cityType == "울산시") {
                                    userDataClass.city = binding.ulsan.selectedItem.toString()
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
                            if (binding.nameInfo.text.isNullOrBlank() || binding.idInfo.text.isNullOrBlank() || binding.passwordInfo.text.isNullOrBlank() || binding.companyName.text.isNullOrBlank() || binding.phoneNum.text.isNullOrBlank()) {
                                Toast.makeText(
                                    this@AccountActivity,
                                    "모든 정보가 입력 되지 않았습니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            } else if (checkId == "X") {
                                Toast.makeText(
                                    this@AccountActivity,
                                    "이미 사용하고 있는 아이디입니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else if (binding.passwordInfo.text.toString() != binding.passwordConfirm.text.toString()) {
                                Toast.makeText(
                                    this@AccountActivity,
                                    "비밀번호가 일치하지 않습니다.",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else {


                                var userDataClass = UserDataClass()
                                funImageUpload(viewProfile!!)
                                userDataClass.imageUri = MySharedPreferences.getImage(this@AccountActivity)
                                userDataClass.name = binding.nameInfo.text.toString()
                                userDataClass.loginCheck = "X"
                                userDataClass.id = binding.idInfo.text.toString()
                                userDataClass.password = binding.passwordInfo.text.toString()
                                userDataClass.type = userType
                                userDataClass.writeCount = 0
                                userDataClass.province = cityType
                                userDataClass.phoneNum = binding.phoneNum.text.toString()
                                if (cityType == "서울시") {
                                    userDataClass.city = binding.seoul.selectedItem.toString()
                                } else if (cityType == "경기도") {
                                    userDataClass.city = binding.gyeonggi.selectedItem.toString()
                                } else if (cityType == "인천시") {
                                    userDataClass.city = binding.incheon.selectedItem.toString()
                                } else if (cityType == "충청북도") {
                                    userDataClass.city =
                                        binding.chungcheongbuk.selectedItem.toString()
                                } else if (cityType == "충청남도") {
                                    userDataClass.city =
                                        binding.chungcheongnam.selectedItem.toString()
                                } else if (cityType == "강원도") {
                                    userDataClass.city = binding.gangwon.selectedItem.toString()
                                } else if (cityType == "경상북도") {
                                    userDataClass.city =
                                        binding.gyeongsangbuk.selectedItem.toString()
                                } else if (cityType == "경상남도") {
                                    userDataClass.city =
                                        binding.gyeongsangnam.selectedItem.toString()
                                } else if (cityType == "전라북도") {
                                    userDataClass.city = binding.jeollabuk.selectedItem.toString()
                                } else if (cityType == "전라남도") {
                                    userDataClass.city = binding.jeollanam.selectedItem.toString()
                                } else if (cityType == "제주도") {
                                    userDataClass.city = binding.jeju.selectedItem.toString()
                                } else if (cityType == "부산시") {
                                    userDataClass.city = binding.busan.selectedItem.toString()
                                } else if (cityType == "광주시") {
                                    userDataClass.city = binding.gwangju.selectedItem.toString()
                                } else if (cityType == "대구시") {
                                    userDataClass.city = binding.daegu.selectedItem.toString()
                                } else if (cityType == "대전시") {
                                    userDataClass.city = binding.daejeon.selectedItem.toString()
                                } else if (cityType == "세종시") {
                                    userDataClass.city = binding.sejong.selectedItem.toString()
                                } else if (cityType == "울산시") {
                                    userDataClass.city = binding.ulsan.selectedItem.toString()
                                }


                                userDataClass.company = binding.companyName.text.toString()
                                userDataClass.companyAdr = binding.companyAdr.text.toString()
                                userDataClass.sector = binding.sectorType.selectedItem.toString()

                                if (ContextCompat.checkSelfPermission(
                                        viewProfile!!.context,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {


                                }
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
                    "서울시" -> {
                        cityType = "서울시"
                        binding.seoul.visibility = View.VISIBLE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE

                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }

                    "경기도" -> {
                        cityType = "경기도"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.VISIBLE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE

                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "인천시" -> {
                        cityType = "인천시"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.VISIBLE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE

                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "부산시" -> {
                        cityType = "부산시"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.VISIBLE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE

                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "대구시" -> {
                        cityType = "대구시"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.VISIBLE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE

                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "대전시" -> {
                        cityType = "대전시"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.VISIBLE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE

                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "광주시" -> {
                        cityType = "광주시"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.VISIBLE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE

                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "울산시" -> {
                        cityType = "울산시"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.VISIBLE
                        binding.sejong.visibility = View.GONE
                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "세종시" -> {
                        cityType = "세종시"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.VISIBLE
                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "강원도" -> {
                        cityType = "강원도"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE
                        binding.gangwon.visibility = View.VISIBLE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "충청북도" -> {
                        cityType = "충청북도"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE
                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.VISIBLE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "충청남도" -> {
                        cityType = "충청남도"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE
                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.VISIBLE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "경상북도" -> {
                        cityType = "경상북도"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE
                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.VISIBLE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "경상남도" -> {
                        cityType = "경상남도"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE
                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.VISIBLE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "전라북도" -> {
                        cityType = "전라북도"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE
                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.VISIBLE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.GONE
                    }
                    "전라남도" -> {
                        cityType = "전라남도"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE
                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.VISIBLE
                        binding.jeju.visibility = View.GONE
                    }
                    "제주도" -> {
                        cityType = "제주도"
                        binding.seoul.visibility = View.GONE
                        binding.gyeonggi.visibility = View.GONE
                        binding.incheon.visibility = View.GONE
                        binding.busan.visibility = View.GONE
                        binding.daegu.visibility = View.GONE
                        binding.daejeon.visibility = View.GONE
                        binding.gwangju.visibility = View.GONE
                        binding.ulsan.visibility = View.GONE
                        binding.sejong.visibility = View.GONE
                        binding.gangwon.visibility = View.GONE
                        binding.chungcheongbuk.visibility = View.GONE
                        binding.chungcheongnam.visibility = View.GONE
                        binding.gyeongsangbuk.visibility = View.GONE
                        binding.gyeongsangnam.visibility = View.GONE
                        binding.jeollabuk.visibility = View.GONE
                        binding.jeollanam.visibility = View.GONE
                        binding.jeju.visibility = View.VISIBLE
                    }

                }
            }
        }
        binding.requestAuthNum.setOnClickListener {
            hideKeyboard()
            if (binding.phoneNum.text.trim().isEmpty()

            ) {
                Toast.makeText(this, "전화번호를 입력해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            } else if (binding.phoneNum.length() < 13
            ) {
                Toast.makeText(this, "전화번호를 모두 입력해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            } else {
                val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
                    override fun onVerificationFailed(e: FirebaseException) {
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        this@AccountActivity.verificationId = verificationId
                    }
                }

                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(
                        phoneNumber82(
                            binding.phoneNum.text.toString().replace("[^0-9]".toRegex(), "")

                        )
                    )
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(callbacks)
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
                auth.setLanguageCode("kr")


            }


        }
        binding.requestAuthNum.setOnClickListener {
            hideKeyboard()
            if (binding.phoneNum.text.trim().isEmpty()

            ) {
                Toast.makeText(this, "전화번호를 입력해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            } else if (binding.phoneNum.length() < 12
            ) {
                Toast.makeText(this, "전화번호를 모두 입력해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            } else {
                val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
                    override fun onVerificationFailed(e: FirebaseException) {
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        this@AccountActivity.verificationId = verificationId
                    }
                }

                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(
                        phoneNumber82(
                            binding.phoneNum.text.toString().replace("[^0-9]".toRegex(), "")

                        )
                    )
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(callbacks)
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
                auth.setLanguageCode("kr")

            }


        }

        binding.authConfirm.setOnClickListener {
            if (binding.phoneNumConfirm.text.trim().isEmpty()

            ) {
                Toast.makeText(this, "인증번호를 입력해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            } else if (binding.phoneNumConfirm.length() < 6) {
                Toast.makeText(this, "인증번호를 모두 입력해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            } else {
                val credential = PhoneAuthProvider.getCredential(
                    verificationId,
                    binding.phoneNumConfirm.text.toString()
                )
                signInWithPhoneAuthCredential(credential)
            }
            hideKeyboard()
        }


        binding.phoneNum.addTextChangedListener(PhoneNumberFormattingTextWatcher("KR"))
    }

    private fun phoneNumber82(msg: String): String {
        val firstNumber: String = msg.substring(0, 3)
        var phoneEdit = msg.substring(3)

        when (firstNumber) {
            "010" -> phoneEdit = "+8210$phoneEdit"
            "011" -> phoneEdit = "+8211$phoneEdit"
            "016" -> phoneEdit = "+8216$phoneEdit"
            "017" -> phoneEdit = "+8217$phoneEdit"
            "018" -> phoneEdit = "+8218$phoneEdit"
            "019" -> phoneEdit = "+8219$phoneEdit"
            "106" -> phoneEdit = "+82106$phoneEdit"
        }
        return phoneEdit
    }


    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode==IMAGE_PICK&&resultCode== Activity.RESULT_OK){
//            selectImage=data?.data
//            imageIv.setImageURI(selectImage)
//        }
//    }
    private fun funImageUpload(view: View) {
        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imgFileName = "IMAGE_" + timeStamp + "_.png"
        var storageRef = fbStorage?.reference?.child("image")?.child(imgFileName)

        val imageUrl = "https://firebasestorage.googleapis.com/v0/b/kingbus-driver.appspot.com/o/image%2F$imgFileName?alt=media"
      MySharedPreferences.setImage(this, imageUrl)

        storageRef?.putFile(uriPhoto!!)?.addOnSuccessListener {
            Toast.makeText(this, "이미지 업로드", Toast.LENGTH_SHORT).show()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageFromAlbum) {
            if (resultCode == Activity.RESULT_OK) {
                uriPhoto = data?.data
                binding.imgBtn.setImageURI(uriPhoto)


            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //인증성공
                    binding.authBtn.isEnabled = true
                    Toast.makeText(this, "인증에 성공했습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    //인증실패
                    Toast.makeText(this, "인증에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.main.windowToken, 0)
    }
}