package com.example.learningapp.firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityLogInWithPhoneNumberBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LogInWithPhoneNumber : AppCompatActivity() {
    private lateinit var binding: ActivityLogInWithPhoneNumberBinding
    private lateinit var auth: FirebaseAuth
    private var verificationId: String? = null

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
        auth = FirebaseAuth.getInstance()
        onClickSubmit()
    }

    private fun onClickSubmit() {
        binding.btSubmit.setOnClickListener {
            val phoneNumber = binding.txtPhoneNumber.text.toString().trim()
            if (phoneNumber.length < 10) {
                Toast.makeText(this, "Please Enter Valid Number", Toast.LENGTH_LONG).show()
            } else {
                val fullNumber = "+91$phoneNumber"
                sendVerificationCode(fullNumber, auth, this)
            }
        }
    }

    companion object {
        fun sendVerificationCode(number: String, auth: FirebaseAuth, activity: AppCompatActivity) {
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number)
                .setActivity(activity)
                .setTimeout(60L,
                    TimeUnit.SECONDS)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        e.localizedMessage?.let { Log.d("VerificationFailed", it) }
                        Toast.makeText(activity, "Verification failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        Toast.makeText(activity, "OTP sent", Toast.LENGTH_SHORT).show()

                        val intent = Intent(activity, OtpActivity::class.java)
                        intent.putExtra("verificationId", verificationId)
                        activity.startActivity(intent)
                    }
                })
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }
}
