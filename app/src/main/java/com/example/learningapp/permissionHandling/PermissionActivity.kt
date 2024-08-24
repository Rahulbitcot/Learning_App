package com.example.learningapp.permissionHandling

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityPermissionBinding
import com.example.learningapp.databinding.ActivitySharedPrefBinding
import com.example.learningapp.permissionHandling.permissionUtilClass.PermissionUtil

class PermissionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPermissionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCameraPermission.setOnClickListener {
            if (!PermissionUtil.checkCameraPermission(this)) {
                PermissionUtil.requestCameraPermission(this, this)
            }else{
                Toast.makeText(this,"Permission Already Granted" , Toast.LENGTH_LONG).show()
            }
        }

        binding.btnCheckAudioPermission.setOnClickListener {
            if (!PermissionUtil.checkAudioPermission(this)) {
                PermissionUtil.requestAudioPermission(this, this)
            }else{
                Toast.makeText(this,"Permission Already Granted" , Toast.LENGTH_LONG).show()
            }
        }

        binding.btnCheckNotificationPermission.setOnClickListener {
            if(Build.VERSION.SDK_INT > 32) {
                if (!PermissionUtil.checkNotificationPermission(this)) {
                    PermissionUtil.requestNotificationPermission(this, this)
                } else {
                    Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "SDK < 32  did not require Notification runtime permission", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnCheckStoragePermission.setOnClickListener {
                if (!PermissionUtil.checkStoragePermission(this)) {
                    PermissionUtil.requestStoragePermission(this, this)
                } else {
                    Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_LONG).show()
            }
        }
    }
}