package com.example.learningapp.roomDb.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learningapp.roomDb.database.ContactDatabase

class RoomViewModelFactory (private val database: ContactDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoomViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}