package com.kingbus.driver.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kingbus.driver.databinding.FragmentCommunityBinding
import com.kingbus.driver.databinding.FragmentNaviBinding

class NaviFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentNaviBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNaviBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        db = Firebase.firestore
//        auth = Firebase.auth


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