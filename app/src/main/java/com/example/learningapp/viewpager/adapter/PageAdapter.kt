package com.example.learningapp.viewpager.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.learningapp.viewpager.fragments.FragmentOne
import com.example.learningapp.viewpager.fragments.FragmentSecond
import com.example.learningapp.viewpager.fragments.FragmentThird

class PagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentOne().apply {}
            1 -> FragmentSecond().apply {}
            else -> FragmentThird().apply {}
        }
    }
}
