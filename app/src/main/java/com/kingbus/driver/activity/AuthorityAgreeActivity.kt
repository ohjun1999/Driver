package com.kingbus.driver.activity

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kingbus.driver.R
import com.kingbus.driver.databinding.ActivityAuthorityAgreeBinding

class AuthorityAgreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorityAgreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityAuthorityAgreeBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)

        binding.goInfo.setOnClickListener {
            if (binding.allAgree.isChecked) {
                val intent = Intent(this, AccountActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "모든 항목에 동의해주셔야 합니다.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.allAgree.setOnClickListener { onCheckChanged(binding.allAgree) }
        binding.firstAgree.setOnClickListener { onCheckChanged(binding.firstAgree) }
        binding.secondAgree.setOnClickListener { onCheckChanged(binding.secondAgree) }
        binding.thirdAgree.setOnClickListener { onCheckChanged(binding.thirdAgree) }
        binding.fourthAgree.setOnClickListener { onCheckChanged(binding.fourthAgree) }
        binding.fifthAgree.setOnClickListener { onCheckChanged(binding.fifthAgree) }
        binding.firstTerms.setOnClickListener {
            val intent = Intent(this, TermsFirstActivity::class.java)
            startActivity(intent)
        }
        binding.secondTerms.setOnClickListener {
            val intent = Intent(this, TermsSecondActivity::class.java)
            startActivity(intent)
        }
        binding.thirdTerms.setOnClickListener {
            val intent = Intent(this, TermsThirdActivity::class.java)
            startActivity(intent)
        }
        binding.fourthTerms.setOnClickListener {
            val intent = Intent(this, TermsFourthActivity::class.java)
            startActivity(intent)
        }
        binding.fifthTerms.setOnClickListener {
            val intent = Intent(this, TermsFifthActivity::class.java)
            startActivity(intent)
        }



    }

    private fun onCheckChanged(compoundButton: CompoundButton) {
        when (compoundButton.id) {
            R.id.allAgree -> {
                if (binding.allAgree.isChecked) {
                    binding.firstAgree.isChecked = true
                    binding.secondAgree.isChecked = true
                    binding.thirdAgree.isChecked = true
                    binding.fourthAgree.isChecked = true
                    binding.fifthAgree.isChecked = true
                } else {
                    binding.firstAgree.isChecked = false
                    binding.secondAgree.isChecked = false
                    binding.thirdAgree.isChecked = false
                    binding.fourthAgree.isChecked = false
                    binding.fifthAgree.isChecked = false
                }
            }
            else -> {
                binding.allAgree.isChecked = (
                        binding.firstAgree.isChecked
                                && binding.secondAgree.isChecked
                                && binding.thirdAgree.isChecked
                                && binding.fourthAgree.isChecked
                                && binding.fifthAgree.isChecked
                        )
            }
        }
    }
}