package com.example.learningapp.permissionHandling.permissionUtilClass

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionUtil {

    companion object {


        // check and request camera permission
        fun checkCameraPermission(activity: Activity): Boolean {
            val result = ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.CAMERA
            )
            return result == PackageManager.PERMISSION_GRANTED
        }

        fun requestCameraPermission(context: Context, activity: Activity) {
            val permissions =
                arrayOf(
                    android.Manifest.permission.CAMERA
                )
            ActivityCompat.requestPermissions(activity, permissions, 101)
        }

        // check and request AudioPermission
        fun checkAudioPermission(activity: Activity): Boolean {
            val result = ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.RECORD_AUDIO
            )
            return result == PackageManager.PERMISSION_GRANTED
        }

        fun requestAudioPermission(context: Context, activity: Activity) {
            val permissions =
                arrayOf(
                    android.Manifest.permission.RECORD_AUDIO
                )
            ActivityCompat.requestPermissions(activity, permissions, 102)
        }

        // check and request Notification permission
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        fun checkNotificationPermission(activity: Activity): Boolean {
            val result = ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.POST_NOTIFICATIONS
            )
            return result == PackageManager.PERMISSION_GRANTED
        }

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        fun requestNotificationPermission(context: Context, activity: Activity) {
            val permissions =
                arrayOf(
                    android.Manifest.permission.POST_NOTIFICATIONS
                )
            ActivityCompat.requestPermissions(activity, permissions, 103)
        }

        // check and request Storage permission
        fun checkStoragePermission(activity: Activity): Boolean {
            if(Build.VERSION.SDK_INT >=33){
                val result = ActivityCompat.checkSelfPermission(activity,
                    android.Manifest.permission.READ_MEDIA_IMAGES)
                val result2 = ActivityCompat.checkSelfPermission(activity,
                    android.Manifest.permission.READ_MEDIA_VIDEO)
                val result3 = ActivityCompat.checkSelfPermission(activity,
                    android.Manifest.permission.READ_MEDIA_AUDIO)
                return result == PackageManager.PERMISSION_GRANTED &&
                        result2 == PackageManager.PERMISSION_GRANTED &&
                        result3 == PackageManager.PERMISSION_GRANTED
            }else{
                val result = ActivityCompat.checkSelfPermission(activity,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
                val result2 = ActivityCompat.checkSelfPermission(activity,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                return result == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED
            }
        }

        fun requestStoragePermission(context: Context, activity: Activity) {
          if(Build.VERSION.SDK_INT >=33) {
              val permissionArr = arrayOf(
                  android.Manifest.permission.READ_MEDIA_AUDIO,
                  android.Manifest.permission.READ_MEDIA_VIDEO,
                  android.Manifest.permission.READ_MEDIA_IMAGES
              )
              ActivityCompat.requestPermissions(activity, permissionArr, 104)
          }else{
              val permissionArr = arrayOf(
                  android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                  android.Manifest.permission.READ_EXTERNAL_STORAGE)
              ActivityCompat.requestPermissions(activity, permissionArr, 105)
          }
        }
    }
}