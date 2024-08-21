package com.example.learningapp.retrofit.api

import com.example.learningapp.retrofit.models.RandomUser
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface  {
     @GET("/api")
    suspend fun getUser() : Response<RandomUser>

}