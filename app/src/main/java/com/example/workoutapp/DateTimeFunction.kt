package com.example.workoutapp

import androidx.recyclerview.widget.RecyclerView
import com.chaquo.python.Python
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

val obj = Python.getInstance().getModule("PyScript").callAttr("read_code").toString()

fun getDateTimeData(dateTimeRecyclerView: RecyclerView, dateTimeArrayList: ArrayList<DateTime>)
{
    FirebaseDatabase.getInstance().getReference(obj)
        .addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dateTimeArrayList.clear()
                if(snapshot.exists())
                {
                    for(dateTimeSnapshot in snapshot.children)
                    {
                        val dateTime = dateTimeSnapshot.getValue(DateTime::class.java)
                        dateTimeArrayList.add(dateTime!!)
                    }
                    dateTimeRecyclerView.adapter = DateTimeAdapter(dateTimeArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
}

fun addDateTimeData(process: String)
{
    val dateTimeId = System.currentTimeMillis().toString()

    val dateTime = DateTime(dateTimeId, SimpleDateFormat("dd-M-yyyy").format(Date()).toString(),
        SimpleDateFormat("hh:mm:ss").format(Date()).toString(), process)

    FirebaseDatabase.getInstance().getReference(obj)
        .child("timeID${dateTimeId}").setValue(dateTime)
}

fun addDateTimeData(process: String, dateTimeId: String)
{
    val dateTime = DateTime(dateTimeId, SimpleDateFormat("dd-M-yyyy").format(Date()).toString(),
        SimpleDateFormat("hh:mm:ss").format(Date()).toString(), process)

    FirebaseDatabase.getInstance().getReference(obj)
        .child("timeID${dateTimeId}").setValue(dateTime)
}
