package com.kingbus.driver.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kingbus.driver.databinding.ActivityTermsFourthBinding

class TermsFourthActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTermsFourthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityTermsFourthBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        binding.backKey.setOnClickListener {
            finish()
        }

    }
}