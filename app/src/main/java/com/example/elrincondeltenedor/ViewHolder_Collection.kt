package com.example.elrincondeltenedor

import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.CollectionCardviewBinding
import com.squareup.picasso.Picasso

class ViewHolder_Collection(private val binding: CollectionCardviewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemData, onClick: (ItemData) -> Unit) {
        binding.item = item
        binding.executePendingBindings()

        // Cargar la imagen con Picasso
        Picasso.get()
            .load(item.imageResId)
            .into(binding.imageRestaurant)

        // Agregar un listener de clic
        itemView.setOnClickListener {
            onClick(item)  // Llama al callback cuando se hace clic en el restaurante
        }
    }
}
