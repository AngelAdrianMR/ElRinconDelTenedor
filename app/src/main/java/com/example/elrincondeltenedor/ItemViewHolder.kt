package com.example.elrincondeltenedor

import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.ValoracionesRestauranteBinding

class ItemViewHolder (private val binding: ValoracionesRestauranteBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CardView) {
        binding.item = item
        binding.executePendingBindings()
    }

}