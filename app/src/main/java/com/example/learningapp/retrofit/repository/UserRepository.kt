package com.example.learningapp.retrofit.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learningapp.retrofit.api.ApiInterface
import com.example.learningapp.retrofit.models.RandomUser
import com.example.learningapp.retrofit.db.UserDatabase

class UserRepository (private  val apiInterface: ApiInterface ,
                      private val userDatabase: UserDatabase)
{
     private val userLiveData = MutableLiveData<RandomUser>()

     val user : LiveData<RandomUser>
        get() = userLiveData

    suspend fun getUser(){
        val result = apiInterface.getUser()
        userDatabase.userDao().insertUser(result.body()!!.results)
        if(result.body() !=null){
             userLiveData.postValue(result.body())
        }
    }
}