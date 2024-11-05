package com.example.elrincondeltenedor

import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.CollectionCardviewBinding

class ViewHolder_Collection(private val binding: CollectionCardviewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ItemData_Collection) {
        binding.item = item
        binding.executePendingBindings()
        binding.imageRestaurant.setImageResource(item.imageResId) // Asigna la imagen
    }
}