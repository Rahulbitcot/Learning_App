package com.example.learningapp.database

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

open class MyApplication : Application() {

    companion object {
        private const val COUNTER_KEY = "Counter_Value"
        private const val PREFS_NAME = "learning_app_SharedPref"
        private lateinit var sharedPreferences: SharedPreferences

        fun initialize(context: Context) {
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }


        fun getCounterValue(): Int {
            val counter = sharedPreferences.getInt(COUNTER_KEY, 0)
            return counter
        }

        fun setCounterValue(value: Int) {
            with(sharedPreferences.edit()) {
                putInt(COUNTER_KEY, value)
                apply()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        initialize(this)

        val counter = getCounterValue()
        setCounterValue(counter + 1)
    }
}
