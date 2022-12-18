package com.kingbus.driver.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.R
import com.kingbus.driver.activity.NaviDetailActivity
import com.kingbus.driver.dataclass.NaviDataClass
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NaviAdapter (
    val context: Context,
    private val naviList: ArrayList<NaviDataClass>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // Create a reference with an initial file path and name


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_navi, parent, false)



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as NaviAdapter.ViewHolder).itemView
        val navi: NaviDataClass = naviList[position]
        var db = Firebase.firestore
        var auth: FirebaseAuth = Firebase.auth
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm", Locale("ko", "KR"))
        val nowDate = dateFormat.format(date)



        holder.companyName.text = navi.companyName
        holder.timeNavi.text = navi.startTime + " ~ " + navi.endTime
        holder.locateNavi.text = navi.locate






        holder.itemView.setOnClickListener {
//            Toast.makeText(MainActivity(), navi.type, Toast.LENGTH_SHORT).show()
            val intent =
                Intent(holder.itemView.context, NaviDetailActivity::class.java)


            intent.putExtra("uid", navi.uid)
            intent.putExtra("reviewList", navi.reviewList?.size.toString())
            intent.putExtra("companyName", navi.companyName)
            intent.putExtra("locateDetail", navi.locateDetail)
            intent.putExtra("locate", navi.locate)
            intent.putExtra("startTime", navi.startTime)
            intent.putExtra("endTime", navi.endTime)
            intent.putExtra("context", navi.context)
            intent.putExtra("holiday", navi.holiday)
            intent.putExtra("phoneNum", navi.phoneNum)
            intent.putExtra("callNum", navi.callNum)
            intent.putExtra("personnel", navi.personnel)
            intent.putExtra("parkCheck", navi.parkCheck)
            intent.putExtra("image", navi.image)
            intent.putExtra("type", navi.type)

            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }

    }

    override fun getItemCount(): Int {
        return naviList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyName: TextView = itemView.findViewById(R.id.companyName)
        val timeNavi: TextView = itemView.findViewById(R.id.timeNavi)
        val locateNavi: TextView = itemView.findViewById(R.id.locateNavi)
        val companyImg: ImageView = itemView.findViewById(R.id.companyImg)

    }
}