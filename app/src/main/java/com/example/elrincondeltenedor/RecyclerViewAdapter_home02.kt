package com.example.elrincondeltenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.ValoracionesRestauranteBinding

class RecyclerViewAdapter(private val items: List<CardView>) :
    RecyclerView.Adapter<ItemViewHolder>() {
    private lateinit var binding: ValoracionesRestauranteBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = ValoracionesRestauranteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]

        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Clic en: ${currentItem.text}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    override fun getItemCount(): Int = items.size
}