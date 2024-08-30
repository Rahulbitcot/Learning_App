package com.example.learningapp.firebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivitySignInWithPhoneNumberBinding

class SignInWithPhoneNumber : AppCompatActivity() {
    private lateinit var binding : ActivitySignInWithPhoneNumberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInWithPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onSubmit()
    }

    private fun onSubmit() {
        binding.btSubmit.setOnClickListener{
            if (binding.txtPhoneNumber.text?.length!! < 10){
                Toast.makeText(this, "Please Enter Valid Number", Toast.LENGTH_LONG).show()
            }
        }

    }
}