package com.example.elrincondeltenedor

import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.CollectionCardviewBinding
import com.squareup.picasso.Picasso

class ViewHolder_Collection(private val binding: CollectionCardviewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemData) {
        binding.item = item
        binding.executePendingBindings()
        // Cargar la imagen con Picasso
        Picasso.get()
            .load(item.imageResId)
            .into(binding.imageRestaurant)
        binding.executePendingBindings()
    }
}