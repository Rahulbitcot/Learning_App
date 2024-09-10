package com.example.learningapp.googleOAuth

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningapp.database.MyApplication
import com.example.learningapp.databinding.ActivityGoogleDriveBinding
import com.example.learningapp.googleOAuth.GoogleOAuth.Companion.accessToken
import com.example.learningapp.googleOAuth.adapter.ImagesAdapter

class GoogleDriveActivity : AppCompatActivity(), GoogleDriveCallback {

    private lateinit var binding: ActivityGoogleDriveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleDriveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.VISIBLE

        // Make API call to fetch Google Drive files and download images
        val authUtil = AuthUtil(this)
        authUtil.GetGoogleDriveFilesTask(this).execute(accessToken)

        btnClickLogout()
    }

    private fun btnClickLogout(){
        binding.btnLogout.setOnClickListener{
            with((application as MyApplication).sharedPreferences.edit()) {
                putBoolean((application as MyApplication).isLoggedIn, false)
                apply()
            }
            Toast.makeText(this , "Logout Successfully" ,Toast.LENGTH_SHORT).show()
            val intent = Intent(this ,GoogleOAuth::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Handle the callback when images are received
    override fun onFilesReceived(images: List<Bitmap>) {
        // Setup the RecyclerView to display images
        binding.recyclerViewImages.layoutManager = LinearLayoutManager(this)
        val adapter = ImagesAdapter(images)
        binding.recyclerViewImages.adapter = adapter
        binding.progressBar.visibility= View.GONE
    }
}
