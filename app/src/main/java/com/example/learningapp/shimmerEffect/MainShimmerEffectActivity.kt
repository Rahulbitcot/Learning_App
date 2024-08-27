package com.example.learningapp.shimmerEffect

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.databinding.ActivityMainShimmerEffectBinding

class MainShimmerEffectActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainShimmerEffectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainShimmerEffectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSeeMyStatistics.setOnClickListener(){
            val intent = Intent(this , SecondActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}