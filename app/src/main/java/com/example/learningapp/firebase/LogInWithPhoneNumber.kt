package com.example.learningapp.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityFirebaseMainBinding
import com.example.learningapp.databinding.ActivityLogInWithPhoneNumberBinding

class LogInWithPhoneNumber : AppCompatActivity() {
    private lateinit var binding: ActivityLogInWithPhoneNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogInWithPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onOtpSubmit()

    }

    private fun onOtpSubmit() {
        binding.btSubmit.setOnClickListener {
            if (binding.txtPhoneNumber.text?.length!! < 10){
                Toast.makeText(this, "Please Enter Valid Number", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, OtpActivity::class.java)
                startActivity(intent)
            }
        }

    }
}