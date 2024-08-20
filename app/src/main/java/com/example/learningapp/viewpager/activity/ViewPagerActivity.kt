package com.example.learningapp.viewpager.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityViewPagerBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class ViewPagerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewPagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pagerAdapter = com.example.learningapp.viewpager.adapter.PagerAdapter(this)
        binding.viewPagerOnBoardingScreen.adapter = pagerAdapter

        val dotsIndicator = findViewById<DotsIndicator>(R.id.dots_indicator)
        dotsIndicator.attachTo(binding.viewPagerOnBoardingScreen)

    }
}
