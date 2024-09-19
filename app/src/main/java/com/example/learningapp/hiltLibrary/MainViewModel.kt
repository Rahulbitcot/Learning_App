package com.example.learningapp.hiltLibrary

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    @Named("print1")
    private val repository: MyRepository
) : ViewModel() {

    fun fetchWelcomeMessage(): String {
        return repository.getWelcomeMessage()
    }
}
