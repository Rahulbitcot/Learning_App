package com.example.learningapp.collapseToolbar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityCollapseToolbarBinding

class CollapseToolbar : AppCompatActivity() {
    private lateinit var binding : ActivityCollapseToolbarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCollapseToolbarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "India: A Tapestry of Diversity and Heritage "
    }
}