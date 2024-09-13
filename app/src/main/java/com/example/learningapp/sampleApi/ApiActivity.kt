package com.example.learningapp.sampleApi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.R
import android.widget.LinearLayout
import android.widget.TextView
import com.example.learningapp.databinding.ActivityApiBinding
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

// Retrofit API URLs to fetch data from three different APIs
//https://www.whenisthenextmcufilm.com/api
//https://hacker-news.firebaseio.com/v0/item/8863.json
//https://list.ly/api/v4/meta?url=http://google.com
class ApiActivity : AppCompatActivity() {

    // View binding to access UI elements
    private lateinit var binding: ActivityApiBinding

    // Coroutine scope to handle background tasks on the main thread
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    // onCreate method to set up the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initiate API data fetching
        fetchApiData()
    }

    // Method to fetch data from three different APIs concurrently
    private fun fetchApiData() {
        coroutineScope.launch {
            val results = mutableListOf<String>()

            // Launching asynchronous API calls
            val api1 = async {
                delay(10000)
                apiCall {
                    RetrofitInstance.apiService.fetchApi1()
                }
            }
            val api2 = async {
                apiCall {
                    RetrofitInstance.dogApiService.fetchApi2()
                }
            }
            val api3 = async {
                apiCall {
                    RetrofitInstance.numbersApiService.fetchApi3()
                }
            }

            // Wait for all API results and collect them
            val api1Result = api1.await()
            val api2Result = api2.await()
            val api3Result = api3.await()

            // Add results to the list
            results.add(api1Result)
            results.add(api2Result)
            results.add(api3Result)

            // Display the results in the layout
            appendToLayout(results)
        }
    }

    // Helper method to handle the API call and process the response
    private suspend fun apiCall(apiCall: suspend () -> Response<String>): String {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                "Success: ${response.body()}"
            } else {
                "Failure: ${response.errorBody()?.string()}"
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    // Method to append API results to the LinearLayout as TextViews
    private fun appendToLayout(results: List<String>) {
        val layout = findViewById<LinearLayout>(R.id.resultsLayout)
        binding.resultsLayout.visibility = View.VISIBLE // Make layout visible if results are available

        // Add each result as a new TextView with custom margin
        results.forEach { result ->
            val textView = TextView(this)
            textView.text = result

            // Set layout parameters for the TextView
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(16, 16, 16, 16)
            textView.layoutParams = params
            layout.addView(textView) // Add TextView to the layout
        }
    }

    // Cancel coroutine scope when activity is destroyed to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}

// Object to manage Retrofit instances for different APIs
object RetrofitInstance {

    // Base URLs for different APIs
    private const val BASE_URL1 = "https://www.whenisthenextmcufilm.com"
    private const val BASE_URL2 = "https://hacker-news.firebaseio.com/v0/item/"
    private const val BASE_URL3 = "https://list.ly/api/v4/"

    // Retrofit instance for the first API
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL1)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Retrofit instance for the second API
    val dogApiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Retrofit instance for the third API
    val numbersApiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL3)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
