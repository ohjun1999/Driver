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
import com.kingbus.driver.databinding.FragmentExtraBinding
import com.kingbus.driver.databinding.FragmentManageBinding
import com.kingbus.driver.dataclass.PostDataClass
import com.kingbus.driver.dataclass.UserDataClass

class ExtraFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentExtraBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExtraBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        auth = Firebase.auth
        binding.myName.text = arguments?.getString("name")
        val myUid = arguments?.getString("uid")


        db.collection("User").whereEqualTo("uid", myUid).addSnapshotListener { documents, _ ->
            for (document in documents!!) {
                binding.myBelong.text =
                    document.getString("province") + " " + document.getString("city")
                binding.writeCount.text = document.get("writeCount").toString()


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