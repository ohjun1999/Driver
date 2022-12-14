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
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.activity.MainActivity
import com.kingbus.driver.adapter.NaviAdapter
import com.kingbus.driver.databinding.FragmentNaviBinding
import com.kingbus.driver.dataclass.NaviDataClass

class NaviFragment : Fragment() {
    var firestore: FirebaseFirestore? = null
    private var _binding: FragmentNaviBinding? = null
    private val binding get() = _binding!!
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var naviRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNaviBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        auth = Firebase.auth
        naviRecyclerView = binding.naviRecyclerView
        var naviList = arrayListOf<NaviDataClass>()
        db.collection("Navi").orderBy("companyName", Query.Direction.ASCENDING).get().addOnSuccessListener { documents ->
            naviList.clear()
            for (document in documents) {
                Log.d(document.id, document.data.toString())
                var item = document.toObject(NaviDataClass::class.java)
                naviList.add(item)
            }
            val naviAdapter =
                NaviAdapter(MainActivity(), naviList)
            naviRecyclerView.adapter = naviAdapter
            naviRecyclerView.layoutManager =
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