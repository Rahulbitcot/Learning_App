package com.example.learningapp.liveData.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var count = 0
    private val array = arrayOf("Honey never spoils and can last indefinitely",
        "Cats have five toes on their front paws",
        "The Earth's atmosphere is about 78% nitrogen.",
        "The shortest war in history lasted 38 minutes",
        "An octopus has three hearts and blue blood",
        "A group of flamingos is called a flamboyance")

    private val factLiveMutableData = MutableLiveData<String>()

    val factLiveData: LiveData<String>
        get() = factLiveMutableData

    init {
        factLiveMutableData.value = array[count]
    }

    fun updateText() {
        count++
        if (count >= array.size) {
            count = 0
        }
        factLiveMutableData.value = array[count]
    }
}
