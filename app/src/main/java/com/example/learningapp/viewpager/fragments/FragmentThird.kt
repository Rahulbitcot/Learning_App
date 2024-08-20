package com.example.learningapp.viewpager.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.learningapp.R
import com.example.learningapp.viewpager.activity.MainScreen

class FragmentThird : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bt_GetStart = view.findViewById<Button>(R.id.bt_GetStart)
        bt_GetStart.setOnClickListener{
            val intent = Intent(activity , MainScreen::class.java )
            startActivity(intent)
            activity?.finish()
        }
    }
}