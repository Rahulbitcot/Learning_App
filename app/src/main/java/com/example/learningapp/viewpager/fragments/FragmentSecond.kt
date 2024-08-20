package com.example.learningapp.viewpager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.learningapp.R

class FragmentSecond : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_second, container, false)
        val bt_Next = view?.findViewById<Button>(R.id.bt_Next)
        bt_Next?.setOnClickListener {
            val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager_onBoardingScreen)
            viewPager?.currentItem = 2
        }
        return view
    }

}