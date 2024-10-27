package com.example.elrincondeltenedor

import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.Home02CardviewBinding

class ViewHolder_home02(
    private val binding: Home02CardviewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemData) {
        binding.item = item
        binding.executePendingBindings()
    }
}