package com.kingbus.driver.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.MySharedPreferences
import com.kingbus.driver.R
import com.kingbus.driver.activity.MainActivity
import com.kingbus.driver.activity.NoticeDetailActivity
import com.kingbus.driver.adapter.DriverAdapter
import com.kingbus.driver.adapter.PostAdapter
import com.kingbus.driver.databinding.FragmentFreeBinding
import com.kingbus.driver.dataclass.PostDataClass
import kotlinx.coroutines.*

class ComChildFreeFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentFreeBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var freeRecyclerView: RecyclerView
    lateinit var job: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFreeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        auth = Firebase.auth
        val userUid = MySharedPreferences.getUserUid(requireContext())
        val userName = MySharedPreferences.getName(requireContext())
        freeRecyclerView = binding.freeRecyclerView
        var postList = arrayListOf<PostDataClass>()

        db.collection("User").whereEqualTo("uid", userUid).limit(1)
            .addSnapshotListener { documents, _ ->


                for (document in documents!!) {
                    var blockList: ArrayList<String> = document.get("block") as ArrayList<String>

                }


            }
        db.collection("Notice").whereEqualTo("type", "자유").limit(1)
            .addSnapshotListener { documents, _ ->


                for (document in documents!!) {

                    binding.postTitle.text = document.getString("title")

                    if (document.get("comment").toString() == "0"){
                        binding.countComment.visibility = View.GONE
                    }else{
                        binding.countComment.text = "(" + document.get("comment").toString() + ")"
                    }

                    binding.postPubDate.text = document.getString("pubDate")

                }


            }
        binding.postItem.setOnClickListener {
            val intent = Intent(activity, NoticeDetailActivity::class.java)
            intent.putExtra("theType", "자유")
            startActivity(intent)
        }
        db.collection("Post").whereEqualTo("type", "자유")
            .orderBy("pubDate", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { documents ->
                postList.clear()
                for (document in documents) {
                    Log.d(document.id, document.data.toString())
                    var item = document.toObject(PostDataClass::class.java)
                    postList.add(item)
                }
               val postAdapter =
                PostAdapter(MainActivity(), postList , userName)
                freeRecyclerView.adapter = postAdapter
                freeRecyclerView.layoutManager =
                    LinearLayoutManager(MainActivity(), RecyclerView.VERTICAL, false)


            }
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            db.collection("Post").whereEqualTo("type", "자유")
                .orderBy("pubDate", Query.Direction.DESCENDING).get()
                .addOnSuccessListener { documents ->
                    postList.clear()
                    for (document in documents) {
                        Log.d(document.id, document.data.toString())
                        var item = document.toObject(PostDataClass::class.java)
                        postList.add(item)
                    }
                    val postAdapter =
                        PostAdapter(MainActivity(), postList,userName)
                    freeRecyclerView.adapter = postAdapter
                    freeRecyclerView.layoutManager =
                        LinearLayoutManager(MainActivity(), RecyclerView.VERTICAL, false)


                }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}