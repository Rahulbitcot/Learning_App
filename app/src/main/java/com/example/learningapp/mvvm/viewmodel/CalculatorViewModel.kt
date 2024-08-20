package com.example.learningapp.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.example.learningapp.mvvm.model.CalculatorModel

class CalculatorViewModel :ViewModel(){

    fun result(num1 :Int, num2 : Int) : CalculatorModel {
        val sum = num1+num2
        return CalculatorModel(num1, num2, sum)
    }
}