package com.example.learningapp.googleOAuth

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

// Access to Drive data use this function
class AuthUtil(private val callback: GoogleDriveCallback) {

    // Pass the callback to the constructor of GetGoogleDriveFilesTask
    inner class GetGoogleDriveFilesTask(private val callback: GoogleDriveCallback) : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg tokens: String?): String {
            val accessToken = tokens[0]
            val url = URL("https://www.googleapis.com/drive/v3/files")

            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Authorization", "Bearer $accessToken")

            return try {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                reader.forEachLine { line -> response.append(line) }
                response.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                "Error: ${e.message}"
            } finally {
                connection.disconnect()
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d("GoogleDrive", "Files: $result")
            if (result != null) {
                // Use the callback to return the result
                callback.onFilesReceived(result)
            }
        }
    }
}


interface GoogleDriveCallback {
    fun onFilesReceived(result: String)
}
