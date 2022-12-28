package com.kingbus.driver.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.MySharedPreferences
import com.kingbus.driver.adapter.PostAdapter
import com.kingbus.driver.databinding.ActivityMyWriteBinding
import com.kingbus.driver.dataclass.PostDataClass

class MyWriteActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityMyWriteBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var writeRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityMyWriteBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        val uid = intent.getStringExtra("uid")
        val writeCount = intent.getStringExtra("writeCount")
        val userUid = MySharedPreferences.getUserUid(this)
        val userName = MySharedPreferences.getName(this)
        binding.backKey.setOnClickListener {
            finish()
        }
        binding.writeCount.text = writeCount + "개"
        writeRecyclerView = binding.writeRecyclerView
        var postList = arrayListOf<PostDataClass>()
        db.collection("User").whereEqualTo("uid", userUid).limit(1)
            .addSnapshotListener { documents, _ ->


                for (document in documents!!) {
                    var blockList: ArrayList<String> = document.get("block") as ArrayList<String>
                    db.collection("Post").whereEqualTo("uid", uid).addSnapshotListener { documents, _ ->
                        postList.clear()
                        for (document in documents!!) {
                            Log.d(document.id, document.data.toString())
                            var item = document.toObject(PostDataClass::class.java)
                            postList.add(item)
                        }
                        val postAdapter =
                            PostAdapter(MainActivity(), postList,userUid,blockList)
                        writeRecyclerView.adapter = postAdapter
                        writeRecyclerView.layoutManager =
                            LinearLayoutManager(MyWriteActivity(), RecyclerView.VERTICAL, false)

                    }


                }


            }







    }

//    fun hideKeyboard() {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(binding.write.windowToken, 0)
//    }




}