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
        binding.descriptionRestaurant.text = item.text

        // Cargar la imagen con Picasso desde la URL
        Picasso.get()
            .load(item.imageResId)  // Aquí pasas la URL de la imagen
            .into(binding.imageRestaurant)     // Se asigna a la vista de imagen

        binding.executePendingBindings() // Para asegurarse de que la vista se actualiza inmediatamente
    }
}