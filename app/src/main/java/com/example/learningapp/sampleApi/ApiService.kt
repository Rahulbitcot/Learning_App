package com.example.learningapp.sampleApi

import retrofit2.Response
import retrofit2.http.GET

// Interface for defining API service methods
// Contains API endpoints for fetching data from different APIs

// API 1: MCU Film API - Fetches the next MCU film details
// https://www.whenisthenextmcufilm.com/api
interface ApiService {

    // Fetches data from the MCU API endpoint
    @GET("/api")
    suspend fun fetchApi1(): Response<String>

    // API 2: Hacker News API - Fetches specific item details
    // https://hacker-news.firebaseio.com/v0/item/8863.json
    @GET("8863.json")
    suspend fun fetchApi2(): Response<String>

    // API 3: List.ly API - Fetches metadata about a provided URL
    // https://list.ly/api/v4/meta?url=http://google.com
    @GET("meta?url=http://google.com")
    suspend fun fetchApi3(): Response<String>
}