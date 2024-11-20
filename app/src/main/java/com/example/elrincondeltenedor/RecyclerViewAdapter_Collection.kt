package com.example.elrincondeltenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.CollectionCardviewBinding

class RecyclerViewAdapter_Collection(
    private val restaurantList: MutableList<ItemData>,
    private val onRestaurantClick: (ItemData) -> Unit  // Función para manejar el clic
) : RecyclerView.Adapter<ViewHolder_Collection>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Collection {
        val binding = CollectionCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder_Collection(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder_Collection, position: Int) {
        val restaurant = restaurantList[position]
        // Pasamos el restaurante y la función de clic
        holder.bind(restaurant, onRestaurantClick)
    }

    override fun getItemCount(): Int = restaurantList.size
}
