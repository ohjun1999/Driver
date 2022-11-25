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
import com.kingbus.driver.MySharedPreferences
import com.kingbus.driver.activity.MainActivity
import com.kingbus.driver.adapter.JobAdapter
import com.kingbus.driver.adapter.PostAdapter
import com.kingbus.driver.databinding.FragmentJobBinding
import com.kingbus.driver.dataclass.JobDataClass
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
        val userUid = MySharedPreferences.getUserUid(requireContext())
        val userSubmit = MySharedPreferences.getSubmit(requireContext())
        jobRecyclerView =  binding.jobRecyclerView
        var jobList = arrayListOf<JobDataClass>()

        db
            .collection("Job").addSnapshotListener { documents, _ ->
                jobList.clear()
            for (document in documents!!) {
                Log.d(document.id, document.data.toString())
                var item = document.toObject(JobDataClass::class.java)
                jobList.add(item)
            }
//                db
//                    .collection("User").whereEqualTo("uid", userUid)
//                    .get().addOnSuccessListener { result ->
//
//
//                    }
            val jobAdapter =
                JobAdapter(userSubmit, userUid, MainActivity(), jobList)
            jobRecyclerView.adapter = jobAdapter
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