package com.example.elrincondeltenedor

import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.ItemCardviewValoracionesBinding

class ViewHolder_Valoraciones(private val binding: ItemCardviewValoracionesBinding  ) : RecyclerView.ViewHolder(binding.root) {
    fun bind (item:ItemData_Valoraciones) {
        binding.item = item
        binding.executePendingBindings()
    }
}