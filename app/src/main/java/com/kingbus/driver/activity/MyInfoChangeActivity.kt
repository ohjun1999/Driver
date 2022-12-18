package com.kingbus.driver.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.databinding.ActivityMyInfoChangeBinding
import java.text.SimpleDateFormat
import java.util.*

class MyInfoChangeActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityMyInfoChangeBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityMyInfoChangeBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        binding.backKey.setOnClickListener {
            finish()
        }
        val uid = intent.getStringExtra("uid")
        val type = intent.getStringExtra("type")
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm", Locale("ko", "KR"))
        val nowDate = dateFormat.format(date)
        val oldNow = nowDate.toString().substring(0, 4)
        binding.typeInfo.text = type.toString()
        var cityType = "서울"
        db.collection("User").whereEqualTo("uid", uid).addSnapshotListener { documents, _ ->
            for (document in documents!!) {
                binding.nameInfo.setText(document.getString("name"))
                binding.phoneNumInfo.setText(document.getString("phoneNum"))

                if (document.getString("company") != null) {
                    binding.companyInfo.setText(document.getString("company"))
                }
                if (document.getString("birth") != null) {
                    val myDate = document.getString("birth").toString().substring(0, 4)
                    val myOld = oldNow.toInt() - myDate.toInt() + 1
                    binding.oldInfo.setText(myOld.toString())
                }

                when (document.getString("province")) {
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

                when (type.toString()) {

                    "일반회원" -> {
//                        binding.oldInfo.setText(document.getString("old"))

                    }

                    "전세버스" -> {

                        binding.companyInfo.setText(document.getString("company"))

                    }
                    "제휴업체" -> {
                        binding.companyInfo.setText(document.getString("company"))
                    }


                }


            }

        }


        when (type.toString()) {

            "일반회원" -> {
                binding.companyNameL.visibility = View.GONE
                binding.companyAdressL.visibility = View.GONE
                binding.howCarL.visibility = View.GONE
            }

            "전세버스" -> {

                binding.genderL.visibility = View.GONE
                binding.companyAdressL.visibility = View.GONE
                binding.oldL.visibility = View.GONE

            }
            "제휴업체" -> {
                binding.genderL.visibility = View.GONE
                binding.howCarL.visibility = View.GONE
                binding.oldL.visibility = View.GONE
            }


        }

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
        binding.saveBtn.setOnClickListener {

            db.collection("User").document(uid.toString())
                .update("name", binding.nameInfo.text.toString())
            db.collection("User").document(uid.toString())
                .update("company", binding.companyInfo.text.toString())
            db.collection("User").document(uid.toString()).update("province", cityType)
            if (cityType == "서울시") {

                val city = binding.seoul.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "경기도") {
                val city = binding.gyeonggi.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "인천시") {
                val city = binding.incheon.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "충청북도") {
                val city = binding.chungcheongbuk.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "충청남도") {
                val city = binding.chungcheongnam.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "강원도") {
                val city = binding.gangwon.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "경상북도") {
                val city = binding.gyeongsangbuk.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "경상남도") {
                val city = binding.gyeongsangnam.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "전라북도") {
                val city = binding.jeollabuk.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "전라남도") {
                val city = binding.jeollanam.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "제주도") {
                val city = binding.jeju.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "부산시") {
                val city = binding.busan.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "광주시") {
                val city = binding.gwangju.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "대구시") {
                val city = binding.daegu.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "대전시") {
                val city = binding.daejeon.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "세종시") {
                val city = binding.sejong.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            } else if (cityType == "울산시") {
                val city = binding.ulsan.selectedItem.toString()
                db.collection("User").document(uid.toString()).update("city", city)
            }
            finish()

        }

    }

//    fun hideKeyboard() {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(binding.write.windowToken, 0)
//    }


}