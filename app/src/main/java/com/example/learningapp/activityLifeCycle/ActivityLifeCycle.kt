package com.example.learningapp.activityLifeCycle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.MainActivity
import com.example.learningapp.databinding.ActivityLifeCycleBinding
import com.google.android.material.button.MaterialButton

class ActivityLifeCycle : AppCompatActivity() {

    private lateinit var binding: ActivityLifeCycleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLifeCycleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBtnClick(binding.btActivityA, ActivityA::class.java )
        onBtnClick(binding.btActivityB, ActivityB::class.java )
        onBtnClick(binding.btBack, MainActivity::class.java )

    }

    private fun onBtnClick(btn : MaterialButton, targetActivity: Class<out AppCompatActivity>){
        btn.setOnClickListener{
            val intent = Intent(this , targetActivity  )
            startActivity(intent)
        }
    }
}
