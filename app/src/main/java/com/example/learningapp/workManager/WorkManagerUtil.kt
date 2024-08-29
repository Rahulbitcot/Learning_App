package com.example.learningapp.workManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.learningapp.R

class WorkManagerUtil {

    companion object {
        private const val CHANNEL_ID = "channel_Id"

        fun generateNotification(context: Context ,title :String ,subTitle : String){
            createNotificationChannel(context)
            Log.d("workManagerLog" , "Updating notification")

            val builder: NotificationCompat.Builder= NotificationCompat.Builder(context , CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(subTitle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, builder.build())
        }
        private fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val serviceChannel = NotificationChannel(
                    CHANNEL_ID,
                    "Work Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                val manager = context.getSystemService(NotificationManager::class.java)
                manager?.createNotificationChannel(serviceChannel)
            }
        }
    }
}