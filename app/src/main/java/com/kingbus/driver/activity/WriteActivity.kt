package com.kingbus.driver.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.kingbus.driver.MySharedPreferences
import com.kingbus.driver.adapter.MultiImageAdapter
import com.kingbus.driver.databinding.ActivityWriteBinding
import com.kingbus.driver.dataclass.JobDataClass
import com.kingbus.driver.dataclass.PostDataClass
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WriteActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityWriteBinding

    var list = ArrayList<Uri>()
    val adapter = MultiImageAdapter(list, this)
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    private var viewProfile: View? = null
    var pickImageFromAlbum = 0
    var fbStorage: FirebaseStorage? = null
    var uriPhoto: Uri? = null
    lateinit var multiImageRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding class 인스턴스 생성
        binding = ActivityWriteBinding.inflate(layoutInflater)
        // binding class의 root를 참조하여 view로
        val view = binding.root
        setContentView(view)
        db = Firebase.firestore
        auth = Firebase.auth
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm", Locale("ko", "KR"))
        val nowDate = dateFormat.format(date)
        val name = intent.getStringExtra("name")
        val uid = intent.getStringExtra("uid")
        binding.backKey.setOnClickListener {
            finish()
        }
        multiImageRecyclerView = binding.multiImageRecyclerView
        viewProfile = binding.imgBtn
        //아이템 선택 리스너
        binding.postType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    //"[ 자유게시판 ]"
                    0 -> {
                        hideKeyboard()
                        if (binding.extra.visibility == View.GONE) {
                            binding.job.visibility = View.GONE
                            binding.extra.visibility = View.VISIBLE
                        }
                    }
                    //"[ 여행게시판 ]"
                    1 -> {
                        hideKeyboard()
                        if (binding.extra.visibility == View.GONE) {
                            binding.job.visibility = View.GONE
                            binding.extra.visibility = View.VISIBLE
                        }
                    }
                    //"[ 공차배차게시판 ]"
                    2 -> {
                        hideKeyboard()
                        if (binding.extra.visibility == View.GONE) {
                            binding.job.visibility = View.GONE
                            binding.extra.visibility = View.VISIBLE
                        }
                    }
                    //"[ 구인구직게시판 ]"
                    3 -> {
                        hideKeyboard()
                        binding.extra.visibility = View.GONE
                        binding.job.visibility = View.VISIBLE
                    }
                    //일치하는게 없는 경우
                    else -> {

                    }
                }
            }
        }
        fbStorage = FirebaseStorage.getInstance()
        binding.imgBtn.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 200)


//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                1
//            )
//            var photoPickerIntent = Intent(Intent.ACTION_PICK)
//            photoPickerIntent.type = "image/*"
//            startActivityForResult(photoPickerIntent, pickImageFromAlbum)

        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        multiImageRecyclerView.layoutManager = layoutManager
        multiImageRecyclerView.adapter = adapter




        binding.endBtn.setOnClickListener {

            var jobDataClass = JobDataClass()
            jobDataClass.company = binding.editTextCompany.text.toString()
            jobDataClass.companyNum = binding.editTextCompanyNum.text.toString()
            jobDataClass.pubDate = nowDate.toString()
            jobDataClass.startOld = binding.startOld.selectedItem.toString()
            jobDataClass.endOld = binding.endOld.selectedItem.toString()
            jobDataClass.city = binding.editTextCity.text.toString()
            jobDataClass.endDate =
                binding.year.selectedItem.toString() + "." + binding.month.selectedItem.toString() + "." + binding.date.selectedItem.toString()
            jobDataClass.context = binding.editTextContent2.text.toString()
            jobDataClass.userUid = uid.toString()
            var postDataClass = PostDataClass()
            postDataClass.uid = uid.toString()
            postDataClass.title = binding.editTextTitle.text.toString()
            postDataClass.context = binding.editTextContent.text.toString()
            postDataClass.pubDate = nowDate.toString()
            postDataClass.comment = 0
            postDataClass.name = name.toString()
            postDataClass.view = 1
            postDataClass.pubDate = nowDate.toString()
            funImageUpload(viewProfile!!)
            when (binding.postType.selectedItem.toString()) {
                "[ 자유게시판 ]" -> {

                    postDataClass.type = "자유"
                    db.collection("User").document(uid.toString())
                        .update("writeCount", FieldValue.increment(1))
                    db.collection("Post")
                        .add(postDataClass)
                        .addOnSuccessListener { documentReference ->

                            val doId = documentReference.id
                            db.collection("Post")
                                .document(doId).update("postUid", doId)
                        }
                        .addOnFailureListener { e ->

                        }
                    hideKeyboard()
                    Toast.makeText(this, "글이 등록 되었습니다.", Toast.LENGTH_SHORT).show()

                    finish()
                }
                "[ 여행게시판 ]" -> {

                    postDataClass.type = "여행"
                    db.collection("User").document(uid.toString())
                        .update("writeCount", FieldValue.increment(1))
                    db.collection("Post")
                        .add(postDataClass)
                        .addOnSuccessListener { documentReference ->

                            val doId = documentReference.id
                            db.collection("Post")
                                .document(doId).update("postUid", doId)
                        }
                        .addOnFailureListener { e ->

                        }
                    hideKeyboard()
                    Toast.makeText(this, "글이 등록 되었습니다.", Toast.LENGTH_SHORT).show()

                    finish()
                }
                "[ 공차배차게시판 ]" -> {


                    postDataClass.type = "공차배차"
                    db.collection("User").document(uid.toString())
                        .update("writeCount", FieldValue.increment(1))
                    db.collection("Post")
                        .add(postDataClass)
                        .addOnSuccessListener { documentReference ->

                            val doId = documentReference.id
                            db.collection("Post")
                                .document(doId).update("postUid", doId)
                        }
                        .addOnFailureListener { e ->

                        }
                    hideKeyboard()
                    Toast.makeText(this, "글이 등록 되었습니다.", Toast.LENGTH_SHORT).show()

                    finish()
                }
                "[ 구인구직게시판 ]" -> {
//                    db.collection("User").document(uid.toString())
//                        .update("writeCount", FieldValue.increment(1))
                    db.collection("Job")
                        .add(jobDataClass)
                        .addOnSuccessListener { documentReference ->

                            val doId = documentReference.id
                            db.collection("Job")
                                .document(doId).update("uid", doId)
                        }
                        .addOnFailureListener { e ->

                        }
                    hideKeyboard()
                    Toast.makeText(this, "글이 등록 되었습니다.", Toast.LENGTH_SHORT).show()

                    finish()

                }
            }


        }


    }

    private fun funImageUpload(view: View) {
        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imgFileName = "IMAGE_" + timeStamp + "_.png"
        var storageRef = fbStorage?.reference?.child("image")?.child(imgFileName)

        val imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/kingbus-driver.appspot.com/o/image%2F$imgFileName?alt=media"
        MySharedPreferences.setImage(this, imageUrl)

        storageRef?.putFile(uriPhoto!!)?.addOnSuccessListener {

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 200) {
            list.clear()

            if (data?.clipData != null) {
                //사진 여러장 선택한 경우
                val count = data.clipData!!.itemCount
                if (count > 10) {
                    Toast.makeText(applicationContext, "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG)

                    return
                }
                for (i in 0 until count) {
                    var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    var imgFileName = "IMAGE_" + timeStamp + "_" + i + "_.png"
                    var storageRef = fbStorage?.reference?.child("image")?.child(imgFileName)

                    val imageUrl =
                        "https://firebasestorage.googleapis.com/v0/b/kingbus-driver.appspot.com/o/image%2F$imgFileName?alt=media"

                    db.collection("Post")
                        .document("PBcrQrDT50rnqBxmZ2F9").update("imgLink", FieldValue.arrayUnion(imageUrl))
                    Log.d("test", imageUrl)

                    val imageUri = data.clipData!!.getItemAt(i).uri
                    list.add(imageUri)

                    storageRef?.putFile(imageUri)?.addOnSuccessListener {

                    }


                }
            } else { //단일선택
                data?.data?.let { uri ->
                    val imageUri: Uri? = data?.data
                    if (imageUri != null) {
                        list.add(imageUri)
                    }

                }

            }

            adapter.notifyDataSetChanged()

        }

//        if (requestCode == pickImageFromAlbum) {
//            if (resultCode == Activity.RESULT_OK) {
//                uriPhoto = data?.data
//                binding.imgBtn.setImageURI(uriPhoto)
//
//
//            }
//        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.write.windowToken, 0)
    }


}