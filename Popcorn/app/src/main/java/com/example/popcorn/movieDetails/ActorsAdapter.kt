package com.example.popcorn.movieDetails

import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.databinding.ActorItemBinding

class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ActorViewHolder>() {
    class ActorViewHolder(val actorItemBinding: ActorItemBinding) :
        RecyclerView.ViewHolder(actorItemBinding.root)

    private var data: List<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            ActorItemBinding.inflate(parent.context.getSystemService()!!, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val item = data?.get(position) ?: return
        holder.actorItemBinding.actorTextView.text = item
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun submitList(list: List<String>) {
        data = list
        notifyDataSetChanged()
    }
}