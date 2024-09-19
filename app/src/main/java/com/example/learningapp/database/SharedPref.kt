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

        var counter = (application as MyApplication).sharedPreferences.
        getInt((application as MyApplication).COUNTER_KEY, 0)

        setCounterValue(counter + 1)
        counter = getCounterValue()

        binding.txtViewCounter.text = buildString {
            append("Activity opened ")
            append(counter)
            append(" times ")
        }
    }
    private fun getCounterValue(): Int {
        val counter = (application as MyApplication).sharedPreferences.getInt((application as MyApplication).COUNTER_KEY, 0)
        return counter
    }

    private fun setCounterValue(value: Int) {
        with((application as MyApplication).sharedPreferences.edit()) {
            putInt((application as MyApplication).COUNTER_KEY, value)
            apply()
        }
    }
}
