package com.example.learningapp.pdfCreation

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityPdfCreationBinding
import com.example.learningapp.pdfCreation.adapter.PdfAdapter
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream

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

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == 101 && resultCode == RESULT_OK) {
                val clipData = data?.clipData
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        selectedImageUris.add(clipData.getItemAt(i).uri)
                    }
                } else {
                    val uri = data?.data
                    if (uri != null) {
                        selectedImageUris.add(uri)
                    }
                }

                Toast.makeText(this, "${selectedImageUris.size} Images Selected", Toast.LENGTH_LONG)
                    .show()

                pdfAdapter = PdfAdapter(selectedImageUris)
                binding.pdfRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.pdfRecyclerView.adapter = pdfAdapter
                binding.pdfRecyclerView.visibility = View.VISIBLE
                binding.infoText.visibility = View.GONE
                pdfAdapter.updateData(selectedImageUris)
            }


            if (requestCode == 102 && resultCode == RESULT_OK) {
                val directoryUri = data?.data ?: return
                createPdfFromImages(directoryUri)
                selectedImageUris.clear()
                binding.pdfRecyclerView.visibility = View.GONE
                binding.infoText.visibility = View.VISIBLE
                pdfAdapter.updateData(selectedImageUris)
            }
        }

    private fun createPdfFromImages(directoryUri: Uri) {
        val document = Document()
//        val pdfFileName = "Images_${System.currentTimeMillis()}.pdf"
        val documentFile = DocumentFile.fromTreeUri(this, directoryUri)
        val pdfFile = documentFile?.createFile("application/pdf", pdfName)

        if (pdfFile != null) {
            val outputStream = contentResolver.openOutputStream(pdfFile.uri)
            if (outputStream != null) {
                PdfWriter.getInstance(document, outputStream)
                document.open()

                for (imageUri in selectedImageUris) {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    val image = Image.getInstance(bitmapToByteArray(bitmap))
                    image.scaleToFit(document.pageSize.width, document.pageSize.height)
                    document.add(image)
                }
                document.close()
                outputStream.close()

                Toast.makeText(this, "PDF saved to ${pdfFile.uri}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed to open output stream", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Failed to create PDF", Toast.LENGTH_SHORT).show()
        }

    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
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
}

