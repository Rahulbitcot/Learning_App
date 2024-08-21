package com.example.learningapp.mvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.learningapp.databinding.ActivityViewBinding
import com.example.learningapp.mvvm.viewmodel.CalculatorViewModel

class ViewActivity : AppCompatActivity() {
   private lateinit var binding : ActivityViewBinding
   private lateinit var calculatorViewModel : CalculatorViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calculatorViewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)

        binding.btResult.setOnClickListener {
            val num1 = binding.editTxtNum1.text.toString().toIntOrNull() ?: 0
            val num2 = binding.editTxtNum1.text.toString().toIntOrNull() ?: 0

            val result = calculatorViewModel.result(num1, num2)
            binding.txtViewResult.text = buildString {
        append(result.sum)
              }
        }
    }
}