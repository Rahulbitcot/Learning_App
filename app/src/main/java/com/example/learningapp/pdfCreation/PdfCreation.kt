package com.example.learningapp.pdfCreation

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityPdfCreationBinding
import com.example.learningapp.pdfCreation.adapter.PdfAdapter
import com.example.learningapp.pdfCreation.pdfUtils.CreatePdfUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PdfCreation : AppCompatActivity() {
    private lateinit var binding: ActivityPdfCreationBinding
    private val selectedImageUris = mutableListOf<Uri>()
    private lateinit var pdfAdapter: PdfAdapter
    private lateinit var pdfName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSelectImages.setOnClickListener {
            val intent = Intent()
            intent.setType("image/*")
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                101
            )
        }

        binding.btSavePdf.setOnClickListener{
            if(selectedImageUris.isNotEmpty()) {
                showSavePdfDialog()
            }else{
                Toast.makeText(this, "Please Select Images" , Toast.LENGTH_LONG).show()
            }
        }
   }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101 && resultCode == RESULT_OK) {
            //For multiple Images
            val clipData = data?.clipData
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    selectedImageUris.add(clipData.getItemAt(i).uri)
                }
            } else {
                //For Single Image
                val uri = data?.data
                if (uri != null) {
                    selectedImageUris.add(uri)
                }
            }

            Toast.makeText(this, "${selectedImageUris.size} Images Selected", Toast.LENGTH_LONG)
                .show()

            updateRecyclerView()
        }
        if (requestCode == 102 && resultCode == RESULT_OK) {
            val directoryUri = data?.data ?: return
            binding.progressBar.visibility = View.VISIBLE
            //Launch coroutines to make pdf in background
            createPdfInBackground(directoryUri)
        }
    }

    private fun updateRecyclerView() {
        pdfAdapter = PdfAdapter(selectedImageUris)
        binding.pdfRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.pdfRecyclerView.adapter = pdfAdapter
        binding.pdfRecyclerView.visibility = View.VISIBLE
        binding.infoText.visibility = View.GONE
        pdfAdapter.updateData(selectedImageUris)
    }

    private fun showSavePdfDialog() {
        val dialog = Dialog(this)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_pdf_name, null)
        dialog.setContentView(view)

        val txtPdfName = view.findViewById<EditText>(R.id.txt_pdfName)
        val btnSavePdf = view.findViewById<Button>(R.id.btnSavePdf)

        dialog.setTitle("Save PDF")
        dialog.setCancelable(true)

        btnSavePdf.setOnClickListener {
            pdfName = txtPdfName.text.toString()
            if(pdfName != "" ) {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                startActivityForResult(intent, 102)
                dialog.dismiss()
            } else {
                Toast.makeText(this , "Please Enter Name " , Toast.LENGTH_LONG).show()
            }
        }
        dialog.show()
    }

    private fun createPdfInBackground(directoryUri : Uri ) {
        CoroutineScope(Dispatchers.IO).launch {
            CreatePdfUtil.createPdfFromImages(contentResolver, pdfName, directoryUri, selectedImageUris, applicationContext)
            withContext(Dispatchers.Main) {
                // Update UI after PDF creation is complete
                 resetUi()
            }
        }
    }

    private fun resetUi() {
        selectedImageUris.clear()
        binding.pdfRecyclerView.visibility = View.GONE
        binding.infoText.visibility = View.VISIBLE
        pdfAdapter.updateData(selectedImageUris)
        binding.progressBar.visibility = View.GONE
    }
}

