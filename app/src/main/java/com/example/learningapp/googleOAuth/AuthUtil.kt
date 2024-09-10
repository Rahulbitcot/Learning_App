package com.example.learningapp.googleOAuth

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

// Utility class to handle Google Drive API interactions
class AuthUtil(private val callback: GoogleDriveCallback) {

    // Task to retrieve Google Drive files and download images
    @SuppressLint("StaticFieldLeak")
    inner class GetGoogleDriveFilesTask(private val callback: GoogleDriveCallback) : AsyncTask<String, Void, List<Bitmap>>() {

        override fun doInBackground(vararg tokens: String?): List<Bitmap> {
            val accessToken = tokens[0]
            val url = URL("https://www.googleapis.com/drive/v3/files?fields=files(id,name,mimeType)")

            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Authorization", "Bearer $accessToken")

            return try {
                // Read the API response
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                reader.forEachLine { line -> response.append(line) }

                // Parse the response and download the image files
                parseFileIdsAndDownloadImages(response.toString(), accessToken!!)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList() // Return an empty list in case of error
            } finally {
                connection.disconnect()
            }
        }

        override fun onPostExecute(result: List<Bitmap>?) {
            super.onPostExecute(result)
            Log.d("GoogleDrive", "Downloaded images: ${result?.size}")
            if (!result.isNullOrEmpty()) {
                // Return the downloaded images to the callback
                callback.onFilesReceived(result)
            }
        }

        // Helper function to parse the response and download image files
        private fun parseFileIdsAndDownloadImages(response: String, accessToken: String): List<Bitmap> {
            val imageList = mutableListOf<Bitmap>()

            // Parse the JSON response to get file metadata (file ID and MIME type)
            val googleDriveResponse = Gson().fromJson(response, GoogleDriveResponse::class.java)

            // Loop through the files and download only images
            googleDriveResponse.files.forEach { file ->
                if (file.mimeType.startsWith("image/")) {
                    val imageUrl = URL("https://www.googleapis.com/drive/v3/files/${file.id}?alt=media")
                    val imageConnection = imageUrl.openConnection() as HttpURLConnection
                    imageConnection.setRequestProperty("Authorization", "Bearer $accessToken")
                    val bitmap = BitmapFactory.decodeStream(imageConnection.inputStream)
                    imageList.add(bitmap)
                    imageConnection.disconnect()
                }
            }
            return imageList
        }
    }
}

// Interface to handle the callback when the files are received
interface GoogleDriveCallback {
    fun onFilesReceived(images: List<Bitmap>)
}

// Data class to map the JSON response from the Google Drive API
data class GoogleDriveResponse(
    @SerializedName("files") val files: List<GoogleDriveFile>
)

data class GoogleDriveFile(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("mimeType") val mimeType: String
)
