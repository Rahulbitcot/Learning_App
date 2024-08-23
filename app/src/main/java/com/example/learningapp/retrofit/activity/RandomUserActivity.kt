package com.example.learningapp.retrofit.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningapp.database.MyApplication
import com.example.learningapp.retrofit.viewModel.MainViewModel
import com.example.learningapp.retrofit.viewModel.MainViewModelFactory
import com.example.learningapp.databinding.ActivityRandomUserBinding
import com.example.learningapp.retrofit.adapter.UserAdapter
import com.example.learningapp.retrofit.db.UserDatabase
import kotlinx.coroutines.launch

class RandomUserActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityRandomUserBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var database: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = (application as MyApplication).userRepository
        database = (application as MyApplication).userDatabase

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository, database))
            .get(MainViewModel::class.java)

        lifecycleScope.launch {
            database.userDao().deleteAllData()
        }

        logcat()
        randomUserRecyclerView()
    }

    private fun randomUserRecyclerView() {
        userAdapter = UserAdapter(emptyList())
        binding.randomUserRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.randomUserRecyclerView.adapter = userAdapter

        mainViewModel.users.observe(this, Observer { results ->
            results?.let {
                userAdapter.updateData(it)
            }
        })
    }

    private fun logcat(){
        mainViewModel.user.observe(this) {
            Log.d("ApiData", it.results.toString())
        }
        database.userDao().getUsers().observe(this){
            Log.d("UserDataBase", it.toString())
        }
    }
}