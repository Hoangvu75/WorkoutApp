package com.example.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.chaquo.python.Python
import com.google.firebase.database.FirebaseDatabase

class DateTimeAdapter(private val dateTimeList: ArrayList<DateTime>):RecyclerView.Adapter<DateTimeAdapter.DateTimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateTimeViewHolder {
        val dateTimeView = LayoutInflater.from(parent.context).inflate(R.layout.date_time_item, parent, false)
        return DateTimeViewHolder(dateTimeView)
    }

    override fun onBindViewHolder(holder: DateTimeViewHolder, position: Int) {
        val currentItem = dateTimeList[position]
        holder.date.text = currentItem.date
        holder.time.text = currentItem.time
        holder.process.text = currentItem.process
        holder.itemView.findViewById<TextView>(R.id.btnDelete).setOnClickListener {
            if(dateTimeList.size == 1)
            {
                holder.item.visibility = View.INVISIBLE
            }
            val obj = Python.getInstance().getModule("PyScript").callAttr("read_code").toString()
            FirebaseDatabase.getInstance().getReference(obj)
                .child("timeID${dateTimeList[position].id}").removeValue()
            DateTimeAdapter(dateTimeList).notifyDataSetChanged()
        }
        if(position % 2 == 0)
        {
            holder.item.setCardBackgroundColor(Color.parseColor("#ECDFFF"))
        }
    }

    override fun getItemCount(): Int {
        return dateTimeList.size
    }

    class DateTimeViewHolder(dateTimeView: View): RecyclerView.ViewHolder(dateTimeView) {
        val date: TextView = dateTimeView.findViewById(R.id.tvDate)
        val time: TextView = dateTimeView.findViewById(R.id.tvTime)
        val process: TextView = dateTimeView.findViewById(R.id.tvProcess)
        val item: CardView = dateTimeView.findViewById(R.id.cvItem)
    }
}