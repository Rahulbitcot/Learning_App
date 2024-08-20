package com.example.learningapp.activityLifeCycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.databinding.ActivityBBinding
import com.google.android.material.button.MaterialButton

class ActivityB : AppCompatActivity() {
    private lateinit var binding :ActivityBBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("Activity_B_Log" , "Activity B is on  onCreate ")

        binding.txtViewInfo.text = buildString {
        append("Activity B, Please Check The Log to get the Current Status of the Activity")
    }
        onBtnClick(binding.btBack, ActivityLifeCycle::class.java )

    }

    private fun onBtnClick(btn : MaterialButton, targetActivity: Class<out AppCompatActivity>) {
        btn.setOnClickListener {
            val intent = Intent(this, targetActivity)
            startActivity(intent)
            //With finish() ,activity will destroy after going to on pause state
            finish()

        }
    }
        override fun onStart() {
            super.onStart()
            Log.d("Activity_B_Log" , "Activity B is on  OnStart ")
        }

        override fun onResume() {
            super.onResume()
            Log.d("Activity_B_Log" , "Activity B is on  onResume ")


        }

        override fun onPause() {
            super.onPause()
            Log.d("Activity_B_Log" , "Activity B is on onPause ")

        }

        override fun onStop() {
            super.onStop()
            Log.d("Activity_B_Log" , "Activity B is on  onStop ")

        }

        override fun onRestart() {
            super.onRestart()
            Log.d("Activity_B_Log" , "Activity B is on  onRestart ")

        }

        override fun onDestroy() {
            super.onDestroy()
            Log.d("Activity_B_Log" , "Activity B is on  onDestroy ")

        }
    //Check log using   package:mine (tag:Activity_B_Log | tag:Activity_A_Log) Filter
}

