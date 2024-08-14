package com.example.learningapp.recyclerView

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningapp.MainActivity
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val p1 = ApiData("566" , "USA" , "2001" ,"125464")
        val p2 = ApiData("566606" , "UdsSA" , "2sd001" ,"125sds464")
        val p3 = ApiData("56dsf6" , "USdfscxA" , "20dsfs01" ,"125sd464")
        val p4 = ApiData("566" , "USA" , "2001" ,"125464")
        val p5 = ApiData("566606" , "UdsSA" , "2012" ,"125sds464")
        val p6 = ApiData("56dscvf6" , "USs" , "2011" ,"125sd464")

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