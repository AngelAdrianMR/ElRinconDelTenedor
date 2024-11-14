package com.example.elrincondeltenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.CollectionCardviewBinding

class RecyclerViewAdapter_Collection(private val items: List<ItemData>) :
    RecyclerView.Adapter<RecyclerViewAdapter_Collection.ViewHolder_Collection>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Collection {
        val binding = CollectionCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder_Collection(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder_Collection, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder_Collection(private val binding: CollectionCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemData) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}