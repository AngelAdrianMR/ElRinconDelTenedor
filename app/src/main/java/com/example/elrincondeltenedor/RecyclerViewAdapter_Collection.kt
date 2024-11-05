package com.example.elrincondeltenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.CollectionCardviewBinding

class RecyclerViewAdapter_Collection(private val items: List<ItemData_Collection>) :
    RecyclerView.Adapter<ViewHolder_Collection>() {

    private lateinit var binding: CollectionCardviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Collection {
        binding = CollectionCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder_Collection(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder_Collection, position: Int) {
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