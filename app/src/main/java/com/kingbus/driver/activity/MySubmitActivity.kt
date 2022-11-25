package com.kingbus.driver.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.adapter.JobAdapter
import com.kingbus.driver.databinding.ActivityMySubmitBinding
import com.kingbus.driver.dataclass.JobDataClass
import com.kingbus.driver.dataclass.UserDataClass

class MySubmitActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityMySubmitBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var submitRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityMySubmitBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        val uid = intent.getStringExtra("uid")
        val submitCount = intent.getStringExtra("submitCount")
        submitRecyclerView = binding.submitRecyclerView
        binding.backKey.setOnClickListener {
            finish()
        }
        binding.submitCount.text = submitCount.toString() + "개"
        var jobList = arrayListOf<JobDataClass>()

        db
            .collection("User").whereEqualTo("uid", uid)
            .get().addOnSuccessListener { result ->
                for (document in result) {
                    var submit: ArrayList<String> = document.get("submit") as ArrayList<String>

                    binding.submitCount.text = submit.size.toString() + "건"
                    for (i in submit) {
                        Log.d("test", i)
                        db
                            .collection("Job").whereEqualTo("uid", i)
                            .get().addOnSuccessListener { results ->
                                for (document in results) {

                                    val item = document.toObject(JobDataClass::class.java)
                                    jobList.add(item)

                                }
                                val jobAdapter =
                                    JobAdapter(submit.toString(),uid.toString(), MySubmitActivity(), jobList)
                                submitRecyclerView.adapter = jobAdapter
                                submitRecyclerView.layoutManager =
                                    LinearLayoutManager(
                                        MySubmitActivity(),
                                        RecyclerView.VERTICAL,
                                        false
                                    )
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