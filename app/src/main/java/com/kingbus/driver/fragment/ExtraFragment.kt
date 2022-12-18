package com.kingbus.driver.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.MySharedPreferences
import com.kingbus.driver.activity.*
import com.kingbus.driver.databinding.FragmentExtraBinding

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
        val type = arguments?.getString("type")

        db.collection("User").whereEqualTo("uid", myUid).addSnapshotListener { documents, _ ->
            for (document in documents!!) {
                binding.myBelong.text =
                    document.getString("province") + " " + document.getString("city")
                val imageUrl = document.getString("imageUri").toString()

                if (imageUrl == "null"){

                }else{
                    Glide.with(this)
                        .load(imageUrl)
                        .into(binding.imageView6)
                }


                if (document.get("writeCount") == null) {
                    binding.writeCount.text = "0"

                } else {
                    binding.writeCount.text = document.get("writeCount").toString()

                }
                var submit: ArrayList<String> = document.get("submit") as ArrayList<String>
                binding.submitCount.text = submit.size.toString()


            }

        }




       binding.goMyWrite.setOnClickListener {
            val intent = Intent(activity, MyWriteActivity::class.java)
            intent.putExtra("uid", myUid)
            intent.putExtra("writeCount", binding.writeCount.text.toString())
            startActivity(intent)
        }
        binding.goMySubmit.setOnClickListener {
            val intent = Intent(activity, MySubmitActivity::class.java)
            intent.putExtra("uid", myUid)
            intent.putExtra("submitCount", binding.submitCount.text.toString())
            startActivity(intent)
        }
        binding.goMyProfile.setOnClickListener {
            val intent = Intent(activity, MyInfoChangeActivity::class.java)
            intent.putExtra("uid", myUid)
            intent.putExtra("type", type)
            startActivity(intent)
        }
        binding.goMyCar.setOnClickListener {
            val intent = Intent(activity, MyCarInfoActivity::class.java)
            startActivity(intent)
        }

        binding.goAlarm.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context?.packageName)
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {

                }
            }
        }
        binding.goMyPassword.setOnClickListener {
            val intent = Intent(activity, PasswordChangeActivity::class.java)
            intent.putExtra("uid", myUid)
            startActivity(intent)
        }

        binding.goLogout.setOnClickListener {
            MySharedPreferences.setLogin(requireContext(), null)
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}