package com.example.learningapp.lottieAnimations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityLottieAnimationBinding


class LottieAnimation : AppCompatActivity() {
    private lateinit var binding :ActivityLottieAnimationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLottieAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lottieAnimationView.playAnimation()

        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chip_startWalking -> {
                    binding.lottieAnimationView.playAnimation()
                }
                R.id.chip_stopWalking -> {
                    binding.lottieAnimationView.pauseAnimation()
                }
            }
        }
    }
}