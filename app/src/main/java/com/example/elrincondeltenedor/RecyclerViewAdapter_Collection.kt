package com.example.elrincondeltenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.CollectionCardviewBinding

class RecyclerViewAdapter_Collection(
    private var items: MutableList<ItemData_Collection>, // Usamos ItemData_Collection
    private val onItemClicked: (ItemData_Collection) -> Unit // Usamos ItemData_Collection
) : RecyclerView.Adapter<ViewHolder_Collection>() {

    fun updateData(newItems: List<ItemData_Collection>) { // Usamos ItemData_Collection
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Collection {
        val binding = CollectionCardviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder_Collection(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder_Collection, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClicked)
    }

    override fun getItemCount(): Int = items.size
}
