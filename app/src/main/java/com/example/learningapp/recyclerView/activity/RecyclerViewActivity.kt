package com.example.learningapp.recyclerView.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningapp.MainActivity
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityRecyclerViewBinding
import com.example.learningapp.recyclerView.adapter.CustomAdapter
import com.example.learningapp.recyclerView.model.ApiData

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

       //Sample Api Data
        val p1 = ApiData("566" , "USA" , "2001" ,"12546564")
        val p2 = ApiData("566606" , "UAE" , "2012" ,"12546564")
        val p3 = ApiData("56dsf6" , "INDIA" , "2008" ,"8965464")
        val p4 = ApiData("566" , "PAKISTAN" , "2001" ,"125464")
        val p5 = ApiData("566606" , "MEXICO" , "2012" ,"989464")
        val p6 = ApiData("56dscf6" , "UKRIAN" , "2011" ,"656564")

        val dataset = arrayOf(p1,p2,p3,p4,p5,p6)

        val customAdapter = CustomAdapter(dataset)

        val recyclerView  = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter

        buttonBack()

    }

    private fun buttonBack() {
        binding.btBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}