package com.example.learningapp.firebase

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityFirebaseMainBinding

class FirebaseMainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFirebaseMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirebaseMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        signInWithPhoneNumber()
        loginWithGoogle()
        loginWithPhoneNumber()
    }

    private fun loginWithPhoneNumber() {
        binding.btLogin.setOnClickListener{
            val intent = Intent(this , LogInWithPhoneNumber::class.java)
            startActivity(intent)
        }
    }

    private fun loginWithGoogle() {

    }

    private fun signInWithPhoneNumber() {
         binding.btLoginWithPhone.setOnClickListener{
             val intent = Intent(this , SignInWithPhoneNumber::class.java )
             startActivity(intent)
         }
    }
}