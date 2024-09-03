package com.example.learningapp.database

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.learningapp.retrofit.api.ApiInterface
import com.example.learningapp.retrofit.api.RetrofitHelper
import com.example.learningapp.retrofit.repository.UserRepository
import com.example.learningapp.retrofit.db.UserDatabase

open class MyApplication : Application() {
     lateinit var sharedPreferences: SharedPreferences
     val COUNTER_KEY = "Counter_Value"
     val LOG_IN_STATUS = "LOG_IN_STATUS"

     private val PREFS_NAME = "learning_app_SharedPref"
     lateinit var userRepository : UserRepository
     lateinit var userDatabase: UserDatabase

    override fun onCreate() {
        super.onCreate()
        initialize(this)
    }

    private fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val apiInterface  = RetrofitHelper.getInstance().create(ApiInterface::class.java)
         userDatabase = UserDatabase.getDatabase(applicationContext)!!
         userRepository = UserRepository(apiInterface , userDatabase)
    }

}
