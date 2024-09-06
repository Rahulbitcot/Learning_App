package com.example.learningapp.googleOAuth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityGoogleDriveBinding
import com.example.learningapp.googleOAuth.GoogleOAuth.Companion.accessToken

class GoogleDriveActivity : AppCompatActivity(),GoogleDriveCallback {

    private lateinit var  binding : ActivityGoogleDriveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGoogleDriveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val authUtil = AuthUtil(this)
        authUtil.GetGoogleDriveFilesTask(this).execute(accessToken)
    }

    // Implement the callback function
    override fun onFilesReceived(result: String) {
        // Handle the result here
        binding.txtViewDriveFiles.text = result.toString()
        println("Received Google Drive files: $result")
        // For example, update the UI with the
    }
}