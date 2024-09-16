package com.example.learningapp.firebaseStorage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learningapp.databinding.ItemImageBinding

class ImagesAdapter(private val images: List<String>) : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = images[position]
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    // ViewHolder class to bind the image to the ImageView
    class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            // Using Glide to load the image from URL into the ImageView
            Glide.with(binding.imageView.context)
                .load(imageUrl)
                .into(binding.imageView)
        }
    }
}
