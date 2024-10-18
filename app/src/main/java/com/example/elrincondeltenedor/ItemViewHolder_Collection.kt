package com.example.elrincondeltenedor

import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.RestaurantCardviewBinding

class ItemViewHolder_Collection (private val binding: RestaurantCardviewBinding ) : RecyclerView.ViewHolder(binding.root) {
    fun  bind(item: ItemData_Collection) {
        binding.item = item
        binding.executePendingBindings()
    }
}