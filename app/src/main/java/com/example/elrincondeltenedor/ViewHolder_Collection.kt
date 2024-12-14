package com.example.elrincondeltenedor

import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.CollectionCardviewBinding
import com.squareup.picasso.Picasso

class ViewHolder_Collection(
    private val binding: CollectionCardviewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemData, onClick: (ItemData) -> Unit) {
        // Asignar valores a las vistas
        binding.nameRestaurant.text = item.name
        binding.descriptionRestaurant.text = item.description

        // Cargar la imagen desde un enlace con Picasso
        Picasso.get()
            .load(item.imageResId.toString()) // Convierte el Int a String si es un enlace
            .placeholder(R.drawable.casa) // Imagen de respaldo mientras se carga
            .error(R.drawable.casa) // Imagen local si ocurre un error
            .into(binding.imageRestaurant)

        // Agregar un listener de clic
        itemView.setOnClickListener {
            onClick(item) // Llama al callback cuando se hace clic en el restaurante
        }
    }
}


