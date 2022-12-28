package com.kingbus.driver.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldValue
import com.kingbus.driver.R
import com.kingbus.driver.dataclass.CommentDataClass
import com.kingbus.driver.dataclass.ReviewDataClass

class ReviewAdapter (
    val context: Context,
    private val reviewList: ArrayList<ReviewDataClass>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // Create a reference with an initial file path and name


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = (holder as ReviewAdapter.ViewHolder).itemView
        val comment: ReviewDataClass = reviewList[position]
        holder.commentContext.text = comment.context
        holder.commentName.text = comment.name
        holder.commentPubDate.text = comment.pubDate
        holder.declaration.setOnClickListener {

        }
        val builder = AlertDialog.Builder(holder.itemView.context)
        holder.declaration.setOnClickListener {
            val dialogView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.custom_dialog, null)
            val dialogTitle = dialogView.findViewById<TextView>(R.id.dialogTitle)

            dialogTitle.setText("본 게시글을 신고하시겠습니까?")
            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */
                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    /* 취소일 때 아무 액션이 없으므로 빈칸 */
                }
                .show()
        }

    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val commentContext: TextView = itemView.findViewById(R.id.commentContext)
        val commentName: TextView = itemView.findViewById(R.id.commentName)
        val commentPubDate: TextView = itemView.findViewById(R.id.commentPubDate)
        val block: TextView = itemView.findViewById(R.id.block)
        val declaration: TextView = itemView.findViewById(R.id.declaration)
//        val deleteBtn: AppCompatButton = itemView.findViewById(R.id.deleteBtn)


    }
}