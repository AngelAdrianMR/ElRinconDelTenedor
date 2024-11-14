package com.example.elrincondeltenedor

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.elrincondeltenedor.databinding.Home02CardviewBinding

class ViewHolder_home02(
    private val binding: Home02CardviewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemData) {
        // Asignar los datos a los elementos de la CardView
        binding.nameRestaurant.text = item.name
        binding.descriptionRestaurant.text = item.description

        // Cargar la imagen con Picasso
        Picasso.get()
            .load(item.imageResId)
            .into(binding.imageRestaurant)

        binding.executePendingBindings() // Para asegurarse de que la vista se actualiza inmediatamente
    }
}