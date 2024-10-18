package com.example.elrincondeltenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.RestaurantCardviewBinding

class RecyclerViewAdapter_Collection (private val items: List<ItemData_Collection>) : RecyclerView.Adapter<ItemViewHolder_Collection>(){
    private lateinit var binding : RestaurantCardviewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder_Collection {
        binding =  RestaurantCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder_Collection(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder_Collection, position: Int) {
        val currentItem = items[position]

        holder.bind(currentItem)

        holder.itemView.setOnClickListener{
            Toast.makeText(holder.itemView.context, "Clic en: ${currentItem.text}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = items.size


}