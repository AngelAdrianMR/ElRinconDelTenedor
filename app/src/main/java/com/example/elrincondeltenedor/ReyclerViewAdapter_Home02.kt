package com.example.elrincondeltenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.Home02CardviewBinding

class RecyclerViewAdapter_Home02(private val items: List<ItemData>) :
    RecyclerView.Adapter<ItemViewHolder_home02>() {
    private lateinit var binding: Home02CardviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder_home02 {
        binding = Home02CardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder_home02(binding)
    }



    override fun onBindViewHolder(holder: ItemViewHolder_home02, position: Int) {
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