package com.example.elrincondeltenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.ItemCardviewValoracionesBinding

class RecyclerViewAdapter_Valoraciones (private val items: List<ItemData>) :
    RecyclerView.Adapter<ViewHolder_Valoraciones>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Valoraciones {
        val binding = ItemCardviewValoracionesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder_Valoraciones(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder_Valoraciones, position: Int) {
        val currentItem = items[position]

        holder.bind(currentItem)

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Click en:", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = items.size
}