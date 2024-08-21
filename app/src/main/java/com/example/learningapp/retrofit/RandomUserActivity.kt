package com.example.learningapp.retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.learningapp.retrofit.api.ApiInterface
import com.example.learningapp.retrofit.api.RetrofitHelper
import com.example.learningapp.retrofit.repository.UserRepository
import com.example.learningapp.retrofit.viewModel.MainViewModel
import com.example.learningapp.retrofit.viewModel.MainViewModelFactory
import com.example.learningapp.databinding.ActivityRandomUserBinding

class RandomUserActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityRandomUserBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiInterface  = RetrofitHelper.getInstance().create(ApiInterface::class.java)

        val repository = UserRepository(apiInterface)
        mainViewModel = ViewModelProvider(this , MainViewModelFactory(repository))
            .get(MainViewModel::class.java)

        mainViewModel.user.observe(this) {
            Log.d("ApiData", it.results.toString())
        }
    }
}