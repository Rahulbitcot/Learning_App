package com.example.learningapp.pdfCreation.pdfUtils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream


class CreatePdfUtil {
    companion object{

        suspend fun createPdfFromImages(contentResolver : ContentResolver, pdfName:String, directoryUri: Uri, selectedImageUris : List<Uri>, context : Context) {
            val document = Document()
            val documentFile = DocumentFile.fromTreeUri(context, directoryUri)
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
                    withContext(Dispatchers.IO) {
                        outputStream.close()
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "PDF saved to ${pdfFile.uri}", Toast.LENGTH_LONG)
                            .show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                       Toast.makeText(context, "Failed to open output stream", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to create PDF", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 40, stream)
            return stream.toByteArray()
        }
    }
}
