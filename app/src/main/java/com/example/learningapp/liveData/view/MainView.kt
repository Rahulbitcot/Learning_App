package com.example.learningapp.liveData.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.learningapp.databinding.ActivityMainViewBinding
import com.example.learningapp.liveData.model.MainViewModel

class MainView : AppCompatActivity() {
    private lateinit var binding : ActivityMainViewBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.factLiveData.observe(this ){
            binding.txtViewLiveData.text = it.toString()
        }

        binding.btnUpdate.setOnClickListener{
            mainViewModel.updateText()

            
        }

    }
}