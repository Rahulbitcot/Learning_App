package com.example.learningapp.fragmentLifeCycle.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.databinding.ActivityFragmentLifeCycleBinding
import com.example.learningapp.fragmentLifeCycle.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class FragmentLifeCycle : AppCompatActivity() {

    private lateinit var binding : ActivityFragmentLifeCycleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentLifeCycleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = Bundle().apply {
            putString("textForFragmentOne", "Text Send From FragmentLifeCycle to Fragment One")
            putString("textForFragmentSecond", "Text Send From FragmentLifeCycle to Fragment Second")
        }

        val pagerAdapter =PagerAdapter(this,bundle)
        binding.viewPager.adapter = pagerAdapter


        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Fragment 1"
                1 -> "fragment 2"
                else -> "Tab"
            }
        }.attach()

    }

    //Check log using { package:mine (tag:Fragment_A_Log | tag:Fragment_B_Log) } Filter

}