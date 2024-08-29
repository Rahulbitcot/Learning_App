package com.example.learningapp.foregroundServices

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.learningapp.databinding.ActivityForegroundServicesBinding
import com.example.learningapp.permissionHandling.permissionUtilClass.PermissionUtil

class ForegroundServices : AppCompatActivity() {
    private lateinit var binding : ActivityForegroundServicesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityForegroundServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT > 32) {
            if (!PermissionUtil.checkNotificationPermission(this)) {
                PermissionUtil.requestNotificationPermission(this, this)
            }
            btnStartClick()
        } else {
            btnStartClick()
        }

        btnStopClick()

    }

    private fun btnStartClick() {
        binding.btStartService.setOnClickListener{
            val serviceIntent = Intent(this, ForegroundServiceUtil::class.java)
            ContextCompat.startForegroundService(this, serviceIntent)
        }
    }

    private fun btnStopClick()
    {
        binding.btStopService.setOnClickListener{
            val serviceIntent = Intent(this, ForegroundServiceUtil::class.java)
            stopService(serviceIntent)
        }
    }
}