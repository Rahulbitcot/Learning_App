package com.example.learningapp.shimmerEffect

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          binding = ActivitySecondBinding.inflate(layoutInflater)
         setContentView(binding.root)

         binding.btnBack.setOnClickListener(){
             val intent = Intent(this , MainShimmerEffectActivity::class.java)
             startActivity(intent)
             finish()
         }
        binding.btnStartListening.setOnClickListener(){
            val intent  = Intent(this , ThirdActivity::class.java)
            startActivity(intent)
        }
        }
}
