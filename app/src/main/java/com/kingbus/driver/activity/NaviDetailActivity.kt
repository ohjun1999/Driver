package com.kingbus.driver.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.MySharedPreferences
import com.kingbus.driver.R
import com.kingbus.driver.adapter.CommentAdapter
import com.kingbus.driver.adapter.ReviewAdapter
import com.kingbus.driver.databinding.ActivityNaviDetailBinding
import com.kingbus.driver.databinding.ActivityPostDetailBinding
import com.kingbus.driver.dataclass.CommentDataClass
import com.kingbus.driver.dataclass.ReviewDataClass
import java.text.SimpleDateFormat
import java.util.*

class NaviDetailActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityNaviDetailBinding

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var reviewRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // binding class 인스턴스 생성
        binding = ActivityNaviDetailBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm", Locale("ko", "KR"))
        val nowDate = dateFormat.format(date)
        val uid = intent.getStringExtra("uid")
        val companyName = intent.getStringExtra("companyName")
        val locate = intent.getStringExtra("locate")
        val locateDetail = intent.getStringExtra("locateDetail")
        val startTime = intent.getStringExtra("startTime")
        val endTime = intent.getStringExtra("endTime")
        val holiday = intent.getStringExtra("holiday")
        val phoneNum = intent.getStringExtra("phoneNum")
        val callNum = intent.getStringExtra("callNum")
        val personnel = intent.getStringExtra("personnel")
        val context = intent.getStringExtra("context")
        val image = intent.getStringExtra("image")
        val type = intent.getStringExtra("type")
        val pubDate = intent.getStringExtra("pubDate")
        val parkCheck = intent.getStringExtra("parkCheck")
        val reviewList1 = intent.getStringExtra("reviewList")
        val userUid = MySharedPreferences.getUserUid(this)
        val userName = MySharedPreferences.getName(this)
        reviewRecyclerView = binding.reviewRecyclerView
        var reviewList = arrayListOf<ReviewDataClass>()
        binding.companyName.text = companyName
        binding.locateNavi.text = locateDetail
        binding.timeNavi.text = "$startTime ~ $endTime"
        binding.holiday.text = "(" + holiday + "은 정기휴일)"
        binding.callNavi.text = "$phoneNum ($callNum)"
        binding.personnelNavi.text = personnel
        binding.context.text = context
        if (parkCheck.toString() == "O") {
            binding.parkNavi.text = "주차장 보유"
        } else {
            binding.parkNavi.text = "주차장 미보유"
        }
        binding.radioButton.setOnClickListener {

            binding.con1.visibility = View.VISIBLE
            binding.con2.visibility = View.GONE
            binding.goBtn.visibility = View.VISIBLE
            binding.review.visibility = View.GONE
        }
        binding.radioButton2.setOnClickListener {

            binding.con1.visibility = View.GONE
            binding.con2.visibility = View.VISIBLE
            binding.goBtn.visibility = View.GONE
            binding.review.visibility = View.VISIBLE
        }

        db.collection("Review").whereEqualTo("naviUid", uid)
            .addSnapshotListener { documents, _ ->
                reviewList.clear()
                for (document in documents!!) {
                    Log.d(document.id, document.data.toString())
                    var item = document.toObject(ReviewDataClass::class.java)
                    reviewList.add(item)
                }
                val reviewAdapter =
                    ReviewAdapter(NaviDetailActivity(), reviewList)
                reviewRecyclerView.adapter = reviewAdapter
                reviewRecyclerView.layoutManager =
                    LinearLayoutManager(MainActivity(), RecyclerView.VERTICAL, false)


            }

        binding.goCall.setOnClickListener {
                val input = phoneNum.toString()
                val myUri = Uri.parse("tel:${input}")
                val myIntent = Intent(Intent.ACTION_DIAL, myUri)
                startActivity(myIntent)

        }
        binding.btnReview.setOnClickListener {
            if (binding.editTextReview.text.isNullOrBlank()) {
                Toast.makeText(this, "작성된 댓글이 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                hideKeyboard()
                var reviewDataClass = ReviewDataClass()
                reviewDataClass.name = userName
                reviewDataClass.context = binding.editTextReview.text.toString()
                reviewDataClass.userUid = userUid
                reviewDataClass.naviUid = uid
                reviewDataClass.pubDate = nowDate.toString()
                binding.editTextReview.setText("")

                db.collection("Review")
                    .add(reviewDataClass)
                    .addOnSuccessListener { documentReference ->

                        val doId = documentReference.id
                        db.collection("Review")
                            .document(doId).update("uid", doId)
                        db.collection("Navi")
                            .document(uid.toString())
                            .update("reviewList", FieldValue.arrayUnion(doId))

                    }
                    .addOnFailureListener { e ->

                    }
            }
        }
//        if (binding.radioButton.isChecked) {
//            binding.con1.visibility = View.VISIBLE
//            binding.con2.visibility = View.GONE
//        } else if (binding.radioButton2.isChecked) {
//            binding.con1.visibility = View.GONE
//            binding.con2.visibility = View.VISIBLE
//            binding.goBtn.visibility = View.GONE
//        }


    }

//    inner class RealNoticeAdapter(
//        val context: Context,
//        var albumImgList: ArrayList<String>,
//
//        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//        var firestore: FirebaseFirestore? = null
//        var db = Firebase.firestore
//        lateinit var auth: FirebaseAuth
//
//
//        override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
//            var view =
//                LayoutInflater.from(parent.context).inflate(R.layout.item_notice_img, parent, false)
//            val olcYear = albumImgList[position]
//
//
//
//            return ViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//            var viewHolder = (holder as RealNoticeAdapter.ViewHolder).itemView
//            val notice = albumImgList[position]
//
//
//            Glide.with(holder.itemView)
//                .load(notice)
//                .into(holder.albumRealImg)
//
//
//        }
//
//        override fun getItemCount(): Int {
//            return albumImgList.size
//        }
//
//
//        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//            val albumRealImg: ImageView = itemView.findViewById(R.id.albumRealImg)
//
//
//        }
//    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.main.windowToken, 0)
    }

}