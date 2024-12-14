package com.example.elrincondeltenedor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.CollectionCardviewBinding
import com.squareup.picasso.Picasso

class RecyclerViewAdapter_Collection(
    private var items: MutableList<ItemData>,
    private val onItemClicked: (ItemData) -> Unit
) : RecyclerView.Adapter<ViewHolder_Collection>() {

    // Actualiza los datos del adaptador
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<ItemData>) {
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
