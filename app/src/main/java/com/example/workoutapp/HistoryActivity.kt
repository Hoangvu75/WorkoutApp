package com.example.workoutapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaquo.python.Python
import com.example.workoutapp.databinding.ActivityHistoryBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null

    private lateinit var dateTimeRecyclerView: RecyclerView
    private lateinit var dateTimeArrayList: ArrayList<DateTime>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistoryActivity)
        if(supportActionBar != null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            finish()
            onBackPressed()
        }

        binding?.btnClearData?.setOnClickListener {
            val obj = Python.getInstance().getModule("PyScript").callAttr("read_code").toString()
            FirebaseDatabase.getInstance().getReference(obj).removeValue()
            dateTimeArrayList.clear()
            dateTimeRecyclerView.adapter?.notifyDataSetChanged()
        }

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        dateTimeRecyclerView = binding?.dateTimeList!!
        dateTimeRecyclerView.layoutManager = linearLayoutManager
        dateTimeRecyclerView.setHasFixedSize(true)
        dateTimeArrayList = arrayListOf<DateTime>()

        getDateTimeData(dateTimeRecyclerView, dateTimeArrayList)
    }
}