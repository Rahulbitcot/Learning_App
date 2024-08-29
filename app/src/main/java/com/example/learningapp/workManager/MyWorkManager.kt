package com.example.learningapp.workManager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkManager(context : Context , params: WorkerParameters): Worker(context , params) {
    override fun doWork(): Result {
          Log.d("workManagerLog" , "workManager started")
        WorkManagerUtil.generateNotification(applicationContext , "Work Manager" , "Work Manager is working .....")
        return Result.success()

    }
}