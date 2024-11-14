package com.example.elrincondeltenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.ItemCardviewValoracionesBinding

class RecyclerViewAdapter_Valoraciones(private val items: List<ItemData_Valoraciones>) :
    RecyclerView.Adapter<RecyclerViewAdapter_Valoraciones.ViewHolder_Valoraciones>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Valoraciones {
        val binding = ItemCardviewValoracionesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder_Valoraciones(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder_Valoraciones, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder_Valoraciones(private val binding: ItemCardviewValoracionesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemData_Valoraciones) {
            binding.item = item
            binding.executePendingBindings()        }
    }
}