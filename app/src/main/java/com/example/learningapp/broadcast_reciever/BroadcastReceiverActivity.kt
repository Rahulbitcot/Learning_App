package com.example.learningapp.broadcast_reciever

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.R

class BroadcastReceiverActivity : AppCompatActivity() {
    private lateinit var airPlanModeReceiver :AirPlanModeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_receiver)
        airPlanModeReceiver = AirPlanModeReceiver()
        airPlanModeReceiver.returnState()

        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(airPlanModeReceiver, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airPlanModeReceiver)
    }
}