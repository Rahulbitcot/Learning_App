package com.example.learningapp.broadcast_reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class AirPlanModeReceiver :BroadcastReceiver() {
    private var  airPlanModeState : Boolean = false
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED){
            val isAirplaneModeEnabled = intent.getBooleanExtra("state", false) ?: return

            if (isAirplaneModeEnabled) {
                airPlanModeState = true
                Toast.makeText(context, "Airplane Mode Enabled", Toast.LENGTH_LONG).show()
            } else {
                airPlanModeState = false
                Toast.makeText(context, "Airplane Mode Disabled", Toast.LENGTH_LONG).show()
            }
        }
        returnState()
    }

    fun returnState():Boolean{
        return airPlanModeState
    }
}