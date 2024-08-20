package com.example.learningapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

open class MyApplication : Application() {

    private val COUNTER_KEY = "Counter_Value"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences("learning_app_SharedPref", Context.MODE_PRIVATE)
        val counter = sharedPreferences.getInt(COUNTER_KEY, 0)
        setCounterValue(counter + 1)
        getCounterValue()
    }

    fun getCounterValue(): Int{
        sharedPreferences = getSharedPreferences("learning_app_SharedPref", Context.MODE_PRIVATE)
        val counter = sharedPreferences.getInt(COUNTER_KEY, 0)
        return counter
    }

    private fun setCounterValue(value: Int) {
        with(sharedPreferences.edit()) {
            putInt(COUNTER_KEY, value)
            apply()
        }
    }
}
