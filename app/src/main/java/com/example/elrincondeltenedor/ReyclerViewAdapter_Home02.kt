package com.example.elrincondeltenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.Home02CardviewBinding
import com.squareup.picasso.Picasso

class RecyclerViewAdapter_Home02(
    private val items: List<ItemData>,
    private val onItemClick: (ItemData) -> Unit // Listener para el clic
) : RecyclerView.Adapter<ViewHolder_home02>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_home02 {
        val binding = Home02CardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder_home02(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder_home02, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)  // Enlazar los datos al ViewHolder

        // Configura el listener de clic en el item
        holder.itemView.setOnClickListener {
            onItemClick(currentItem)  // Llama al listener cuando se hace clic
        }
    }

    override fun getItemCount(): Int = items.size
}