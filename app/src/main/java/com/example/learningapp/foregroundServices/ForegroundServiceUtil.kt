package com.example.learningapp.foregroundServices

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.learningapp.R


class ForegroundServiceUtil : Service() {
    private var count = 0
    private val handler = Handler()
    private val ForegroundServices_ID = 2
    private val SERVICE_CHANNEL_ID = "SERVICE_CHANNEL_ID"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        counter()
        createNotificationChannel()

        val notification: Notification = NotificationCompat.Builder(this, SERVICE_CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Service is running \n counter : $count")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        startForeground(ForegroundServices_ID, notification)

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                SERVICE_CHANNEL_ID,
                "Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            count = if (count < 10000) count + 1 else 0
            updateNotification()
            handler.postDelayed(this, 1000L)
        }
    }

    private fun counter() {
        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    private fun updateNotification() {
        val intent = Intent(this, ForegroundServices::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE)

        val notification: Notification = NotificationCompat.Builder(this, SERVICE_CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Service is running \ncounter: $count")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setNotificationSilent()
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        startForeground(ForegroundServices_ID, notification)
    }
}
