package com.example.popcorn.movieDetails

import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.databinding.GalleryItemBinding

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    class GalleryViewHolder(val galleryItemBinding: GalleryItemBinding) :
        RecyclerView.ViewHolder(galleryItemBinding.root)

    private var data: List<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            GalleryItemBinding.inflate(parent.context.getSystemService()!!, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item = data?.get(position) ?: return
        val context = holder.galleryItemBinding.root.context
        val resource = context.resources.getIdentifier(item, "drawable", context.packageName)
        holder.galleryItemBinding.galleryItemImageView.setImageResource(resource)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    fun submitList(list: List<String>) {
        data = list
        notifyDataSetChanged()
    }
}

