package com.example.elrincondeltenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.CollectionCardviewBinding
import com.example.elrincondeltenedor.databinding.Home02CardviewBinding

class RecyclerViewAdapter_Collection(
    private var items: MutableList<ItemData_Collection>,
    private val onItemClicked: (ItemData_Collection) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter_Collection.ViewHolder>() {

    fun updateData(newItems: List<ItemData_Collection>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = Home02CardviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClicked(item) }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: Home02CardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemData_Collection) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}

