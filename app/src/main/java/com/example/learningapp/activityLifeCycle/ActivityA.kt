package com.example.learningapp.activityLifeCycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.databinding.ActivityABinding
import com.google.android.material.button.MaterialButton


class ActivityA : AppCompatActivity() {
    private lateinit var binding : ActivityABinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityABinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("Activity_A_Log" , "Activity A is on  OnCreate ")

        binding.txtViewInfo.text = "Activity A ,Please Check The Log to get the Current Status of the Activity"
        onBtnClick(binding.btBack, ActivityLifeCycle::class.java )

    }

    private fun onBtnClick(btn : MaterialButton, targetActivity: Class<out AppCompatActivity>){
        btn.setOnClickListener{
            val intent = Intent(this , targetActivity  )
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d("Activity_A_Log" , "Activity A is on  OnStart ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Activity_A_Log" , "Activity A is on  onResume ")


    }

    override fun onPause() {
        super.onPause()
        Log.d("Activity_A_Log" , "Activity A is on  onPause ")

    }

    override fun onStop() {
        super.onStop()
        Log.d("Activity_A_Log" , "Activity A is on  onStop ")

    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Activity_A_Log" , "Activity A is on  onRestart ")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Activity_A_Log" , "Activity A is on  onDestroy ")

    }
}