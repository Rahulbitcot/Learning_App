package com.example.learningapp.pdfCreation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learningapp.R

class PdfAdapter(private var pdfImage : List<Uri>) : RecyclerView.Adapter<PdfAdapter.ViewHolder>() {

       class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
              val imageView: ImageView = view.findViewById(R.id.image)
              val txtPageNo: TextView = view.findViewById(R.id.txt_pageNo)
       }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context)
           .inflate(R.layout.pdf_item_view, parent,false)
        return ViewHolder(view)
     }

    override fun getItemCount(): Int {
      return  pdfImage.size
     }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = pdfImage[position]
        holder.imageView.setImageURI(item)
        holder.txtPageNo.text = "Page No :".plus(position + 1)
    }

    fun updateData(newResults: List<Uri>) {
        pdfImage = newResults
        notifyDataSetChanged()
    }
}