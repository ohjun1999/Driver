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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kingbus.driver.R
import com.kingbus.driver.activity.TravelDetailActivity
import com.kingbus.driver.dataclass.TravelDataClass
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TravelAdapter(
    val context: Context,
    private val travelList: ArrayList<TravelDataClass>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // Create a reference with an initial file path and name


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_travel, parent, false)



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as TravelAdapter.ViewHolder).itemView
        val travel: TravelDataClass = travelList[position]
        var db = Firebase.firestore
        var auth: FirebaseAuth = Firebase.auth
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm", Locale("ko", "KR"))
        val nowDate = dateFormat.format(date)

        holder.tag1.text = "#" + travel.tag1
        holder.tag2.text = "#" + travel.tag2
        holder.tag3.text = "#" + travel.tag3
        holder.mainText.text = travel.title + " (" + travel.travelStart + "출발" +")"
        holder.dateText.text = travel.purchaseStart + " ~ " + travel.purchaseEnd

        holder.itemView.setOnClickListener {
            val intent =
                Intent(holder.itemView.context, TravelDetailActivity::class.java)

            intent.putExtra("title", travel.title)
            intent.putExtra("titleImg", travel.titleImg)
            intent.putExtra("pubDate", travel.pubDate)
            intent.putExtra("travelStart", travel.travelStart)
            intent.putExtra("purchaseStart", travel.purchaseStart)
            intent.putExtra("purchaseEnd", travel.purchaseEnd)
            intent.putExtra("uid", travel.uid)
            intent.putExtra("tag1", travel.tag1)
            intent.putExtra("tag2", travel.tag2)
            intent.putExtra("tag3", travel.tag3)




            ContextCompat.startActivity(holder.itemView.context, intent, null)

        }

    }

    override fun getItemCount(): Int {
        return travelList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mainText: TextView = itemView.findViewById(R.id.mainText)
        val dateText: TextView = itemView.findViewById(R.id.dateText)
        val tag1: TextView = itemView.findViewById(R.id.tag1)
        val tag2: TextView = itemView.findViewById(R.id.tag2)
        val tag3: TextView = itemView.findViewById(R.id.tag3)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

    }
}