package com.example.learningapp.workManager

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.learningapp.databinding.ActivityWorkManagerBinding
import com.example.learningapp.permissionHandling.permissionUtilClass.PermissionUtil
import java.util.concurrent.TimeUnit

class WorkManagerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWorkManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("workManagerLog" , "Service call")

        if(Build.VERSION.SDK_INT >32) {
            if (!PermissionUtil.checkNotificationPermission(this)) {
                PermissionUtil.requestNotificationPermission(this, this)
            }
        }else{
            startWork()
        }
    }

    private  fun startWork() {
        Log.d("workManagerLog" , "Function call")
        val periodicWorkRequest = PeriodicWorkRequestBuilder<MyWorkManager>(
            15,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "periodic_notification",
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkRequest
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("workManagerLog" , "Destroy")
        WorkManager.getInstance(this).cancelUniqueWork("periodic_notification")
    }
}