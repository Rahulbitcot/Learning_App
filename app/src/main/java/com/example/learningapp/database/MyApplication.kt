package com.example.learningapp.database

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

open class MyApplication : Application() {
     lateinit var sharedPreferences: SharedPreferences
     val COUNTER_KEY = "Counter_Value"
     private val PREFS_NAME = "learning_app_SharedPref"

    override fun onCreate() {
        super.onCreate()
        initialize(this)
    }

    private fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

}
