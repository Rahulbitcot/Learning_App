package com.example.learningapp

import com.example.learningapp.recyclerView.RecyclerViewActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {

     private lateinit var btRecyclerView : MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            btRecyclerView = findViewById(R.id.btRecyclerView)
            onBtnClick(btRecyclerView, RecyclerViewActivity::class.java )
        }

    fun onBtnClick(btn : MaterialButton  ,targetActivity: Class<out AppCompatActivity>){
        btn.setOnClickListener{
            val intent = Intent(this , targetActivity  )
            startActivity(intent)
        }
    }
}