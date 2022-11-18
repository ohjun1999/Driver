package com.kingbus.driver.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.R
import com.kingbus.driver.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        db = Firebase.firestore
//        auth = Firebase.auth
        var comChildFreeFragment = ComChildFreeFragment()
        var comChildDriverFragment = ComChildDriverFragment()
        var comChildTravelFragment = ComChildTravelFragment()
        var comChildJobFragment = ComChildJobFragment()
        childFragmentManager.beginTransaction().replace(R.id.communityFrame, comChildDriverFragment).commit()
        binding.btn1.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.communityFrame, comChildDriverFragment).commit()
        }
        binding.btn2.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.communityFrame, comChildFreeFragment).commit()
        }
        binding.btn3.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.communityFrame, comChildTravelFragment).commit()
        }
        binding.btn4.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.communityFrame, comChildJobFragment).commit()
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