package com.kingbus.driver.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.kingbus.driver.databinding.ActivityQesBinding
import com.kingbus.driver.databinding.ActivityWriteBinding
import com.kingbus.driver.dataclass.JobDataClass
import com.kingbus.driver.dataclass.PostDataClass
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class QesActivity : AppCompatActivity() {
    // lateinit 사용
    private lateinit var binding: ActivityQesBinding

    var list = ArrayList<Uri>()
    var imageList = ArrayList<String>()
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
        binding = ActivityQesBinding.inflate(layoutInflater)
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
        val province = intent.getStringExtra("province")
        val city = intent.getStringExtra("city")
        binding.backKey.setOnClickListener {
            finish()
        }
        multiImageRecyclerView = binding.multiImageRecyclerView
        viewProfile = binding.imgBtn
        //아이템 선택 리스너

        fbStorage = FirebaseStorage.getInstance()
        binding.imgBtn.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 200)


            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
//            var photoPickerIntent = Intent(Intent.ACTION_PICK)
//            photoPickerIntent.type = "image/*"
//            startActivityForResult(photoPickerIntent, pickImageFromAlbum)

        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        multiImageRecyclerView.layoutManager = layoutManager
        multiImageRecyclerView.adapter = adapter




        binding.endBtn.setOnClickListener {


            if (binding.editTextTitle.text.trim().isEmpty() || binding.editTextContent.text.trim()
                    .isEmpty()
            ) {
                Toast.makeText(this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT)
                    .show()
            } else {

                var postDataClass = PostDataClass()
                postDataClass.uid = uid.toString()
                postDataClass.title = binding.editTextTitle.text.toString()
                postDataClass.context = binding.editTextContent.text.toString()
                postDataClass.pubDate = nowDate.toString()
                postDataClass.comment = 0
                postDataClass.name = name.toString()
                postDataClass.view = 1
                postDataClass.pubDate = nowDate.toString()
                for (i in 0 until list.size) {
                    funImageUpload(list[i])
                }
                postDataClass.type = "수정요청"
                db.collection("Qes")
                    .add(postDataClass)
                    .addOnSuccessListener { documentReference ->

                        val doId = documentReference.id
                        db.collection("Qes")
                            .document(doId).update("postUid", doId)
                        for (i in imageList) {
                            db.collection("Qes")
                                .document(doId).update("imgLink", FieldValue.arrayUnion(i))
                        }
                    }
                    .addOnFailureListener { e ->

                    }
                hideKeyboard()
                Toast.makeText(this, "글이 등록 되었습니다.", Toast.LENGTH_SHORT).show()

                finish()
            }
        }
    }

    private fun funImageUpload(view: Uri) {
        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imgFileName = "IMAGE_" + timeStamp + "_.png"
        var storageRef = fbStorage?.reference?.child("image")?.child(imgFileName)

        val imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/kingbus-driver.appspot.com/o/image%2F$imgFileName?alt=media"
        MySharedPreferences.setImage(this, imageUrl)

        storageRef?.putFile(view)?.addOnSuccessListener {

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


                    val imageUri = data.clipData!!.getItemAt(i).uri
                    list.add(imageUri)
                    imageList.add(imageUrl)
                    storageRef?.putFile(imageUri)?.addOnSuccessListener {

                    }


                }
            } else { //단일선택
                data?.data?.let { uri ->
                    val imageUri: Uri? = data?.data
                    var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    var imgFileName = "IMAGE_" + timeStamp + "_" + "_.png"
                    val imageUrl =
                        "https://firebasestorage.googleapis.com/v0/b/kingbus-driver.appspot.com/o/image%2F$imgFileName?alt=media"
                    if (imageUri != null) {
                        list.add(imageUri)
                        imageList.add(imageUrl)
                    }

                }

            }

            adapter.notifyDataSetChanged()

        }

        if (requestCode == pickImageFromAlbum) {
            if (resultCode == Activity.RESULT_OK) {
                uriPhoto = data?.data
                binding.imgBtn.setImageURI(uriPhoto)


            }
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.write.windowToken, 0)
    }


}