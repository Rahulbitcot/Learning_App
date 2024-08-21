package com.example.learningapp.retrofit.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningapp.retrofit.models.RandomUser
import com.example.learningapp.retrofit.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel (private  val repository: UserRepository) : ViewModel (){

    init {
        viewModelScope.launch {
            repository.getUser()
        }
    }
    val user : LiveData<RandomUser>
        get()= repository.user
}