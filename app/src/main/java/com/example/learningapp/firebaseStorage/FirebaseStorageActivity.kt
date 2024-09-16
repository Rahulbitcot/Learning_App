package com.example.learningapp.firebaseStorage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityFirebaseStorageBinding
import com.example.learningapp.firebaseStorage.adapter.ImagesAdapter
import com.google.firebase.Timestamp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class FirebaseStorageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFirebaseStorageBinding
    private val uploadedImageUrls: MutableList<String> = mutableListOf()
    private lateinit var adapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFirebaseStorageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.recyclerView.visibility= View.VISIBLE

       binding.btnUploadImage.setOnClickListener {
           val galleryIntent = Intent(Intent.ACTION_PICK)
           galleryIntent.type = "image/*"
           imagePickerActivityResult.launch(galleryIntent)
       }

        // Initialize the RecyclerView
        adapter = ImagesAdapter(uploadedImageUrls)
        binding.recyclerView .adapter = adapter
        binding.recyclerView .layoutManager = LinearLayoutManager(this)
        downloadImg()
    }


    private  var imagePickerActivityResult : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if(result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                if (imageUri != null) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility= View.GONE
                    uploadImage(imageUri)
                }
            }
        }

       private fun uploadImage(imageUri : Uri){
           val storageRef = Firebase.storage.reference
           val currentTime = Timestamp.now().toDate().time
           val filename = "img$currentTime"

           imageUri.let { storageRef.child("File/$filename").putFile(it) }
               .addOnSuccessListener {
                   downloadImg()
                   Toast.makeText(this, "FileUploaded Successfully", Toast.LENGTH_LONG).show()
               }
               .addOnFailureListener {
                   Toast.makeText(this, "File Upload Failed", Toast.LENGTH_LONG).show()
               }
       }

        private fun downloadImg(){
            val storageReference = FirebaseStorage.getInstance().reference.child("File")
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerView.visibility= View.GONE

            uploadedImageUrls.clear()
            storageReference.listAll().addOnSuccessListener { listResult ->
                for (fileReference in listResult.items) {
                    fileReference.downloadUrl.addOnSuccessListener { uri ->
                        uploadedImageUrls.add(uri.toString())
                        // Notify adapter that data has changed
                        adapter.notifyDataSetChanged()
                        binding.recyclerView.visibility= View.VISIBLE
                        binding.progressBar.visibility= View.GONE

                    }.addOnFailureListener {
                        // Handle any errors
                    }
                }
            }.addOnFailureListener {
                // Handle any errors
            }
        }
}