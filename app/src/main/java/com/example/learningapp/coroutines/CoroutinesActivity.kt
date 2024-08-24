package com.example.learningapp.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.learningapp.databinding.ActivityCoroutinesBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutinesActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCoroutinesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startCounter()

    }
    private fun startCounter() {
        lifecycleScope.launch {
            var counter = 0
            while (counter<100000) {
                binding.txtViewCounter.text = "Counter: $counter"
                counter++
                delay(10)
            }
        }
    }
}
