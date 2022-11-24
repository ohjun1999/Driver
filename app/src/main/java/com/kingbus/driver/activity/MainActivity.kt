package com.kingbus.driver.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kingbus.driver.*
import com.kingbus.driver.databinding.ActivityMainBinding
import com.kingbus.driver.fragment.CommunityFragment
import com.kingbus.driver.fragment.ExtraFragment
import com.kingbus.driver.fragment.ManageFragment
import com.kingbus.driver.fragment.NaviFragment

class MainActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityMainBinding

    private var doubleBackToExit = false

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityMainBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
//        db = Firebase.firestore
//        auth = Firebase.auth
        val name = intent.getStringExtra("name")
        val uid = intent.getStringExtra("uid")
        // 하단 탭이 눌렸을 때 화면을 전환하기 위해선 이벤트 처리하기 위해 BottomNavigationView 객체 생성
        var bnvMain = findViewById<BottomNavigationView>(R.id.bnv_main)

        // OnNavigationItemSelectedListener를 통해 탭 아이템 선택 시 이벤트를 처리
        // navi_menu.xml 에서 설정했던 각 아이템들의 id를 통해 알맞은 프래그먼트로 변경하게 한다.
        bnvMain.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.first -> {
                        // 다른 프래그먼트 화면으로 이동하는 기능
                        binding.constraintLayout2.visibility = View.GONE
                        val communityFragment = CommunityFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainFrame, communityFragment).commit()
                        if (binding.writeBtn.visibility == View.GONE) {
                            binding.writeBtn.visibility = View.VISIBLE
                        }

                    }
                    R.id.second -> {
                        val naviFragment = NaviFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainFrame, naviFragment).commit()
                        binding.writeBtn.visibility = View.GONE
                    }
                    R.id.third -> {
                        val manageFragment = ManageFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainFrame, manageFragment).commit()
                        binding.writeBtn.visibility = View.GONE
                    }
                    R.id.four -> {
                        val bundle = Bundle()
                        bundle.putString("name", name)
                        bundle.putString("uid", uid)
                        binding.constraintLayout2.visibility = View.VISIBLE
                        val extraFragment = ExtraFragment()
                        extraFragment.arguments = bundle
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainFrame, extraFragment).commit()
                        binding.writeBtn.visibility = View.GONE
                    }
                }
                true
            }
            selectedItemId = R.id.first
        }
        binding.writeBtn.setOnClickListener {
            var intent = Intent(this, WriteActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("uid", uid)
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

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.main.windowToken, 0)
    }
    fun runDelayed(millis: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(function, millis)
    }
}