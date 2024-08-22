package com.example.learningapp.retrofit.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learningapp.retrofit.db.UserDatabase
import com.example.learningapp.retrofit.repository.UserRepository

class MainViewModelFactory (private val repository: UserRepository, private val database: UserDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(repository ,database) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
    }
}
