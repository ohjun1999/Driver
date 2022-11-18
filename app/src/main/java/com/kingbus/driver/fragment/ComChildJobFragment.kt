package com.kingbus.driver.fragment

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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.activity.MainActivity
import com.kingbus.driver.adapter.PostAdapter
import com.kingbus.driver.databinding.FragmentJobBinding
import com.kingbus.driver.dataclass.PostDataClass

class ComChildJobFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentJobBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var jobRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        auth = Firebase.auth


        jobRecyclerView =  binding.jobRecyclerView
        var postList = arrayListOf<PostDataClass>()

        db.collection("Post").whereEqualTo("type","구인구직").get().addOnSuccessListener { documents ->

            postList.clear()
            for (document in documents) {
                Log.d(document.id, document.data.toString())
                var item = document.toObject(PostDataClass::class.java)
                postList.add(item)
            }

            val postAdapter =
                PostAdapter(MainActivity(), postList)
            jobRecyclerView.adapter = postAdapter
            jobRecyclerView.layoutManager =
                LinearLayoutManager(MainActivity(), RecyclerView.VERTICAL, false)

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