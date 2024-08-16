package com.example.learningapp.fragmentLifeCycle.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.learningapp.fragmentLifeCycle.fragments.Fragment1
import com.example.learningapp.fragmentLifeCycle.fragments.Fragment2

class PagerAdapter(activity: AppCompatActivity, private val data: Bundle) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Fragment1().apply { arguments = data }
            1 -> Fragment2().apply { arguments = data }
            else -> Fragment1()
        }
    }
}
