package com.example.learningapp.database

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.MyApplication
import com.example.learningapp.databinding.ActivitySharedPrefBinding

class SharedPref : AppCompatActivity() {

    private lateinit var binding: ActivitySharedPrefBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedPrefBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val counter = MyApplication.getCounterValue()

        binding.txtViewCounter.text = buildString {
            append("Application opened ")
            append(counter)
            append(" times ")
        }
    }
}
