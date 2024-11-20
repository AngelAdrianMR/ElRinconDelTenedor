package com.example.elrincondeltenedor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.ItemCardviewValoracionesBinding

class RecyclerViewAdapter_Valoraciones(private var items: MutableList<ItemData_Valoraciones>) :
    RecyclerView.Adapter<ViewHolder_Valoraciones>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Valoraciones {
        val binding = ItemCardviewValoracionesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder_Valoraciones(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder_Valoraciones, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = items.size

    // Método para agregar una nueva valoración
    fun addItem(newItem: ItemData_Valoraciones) {
        items.add(newItem)
        notifyItemInserted(items.size - 1) // Notifica al adaptador que se ha agregado un nuevo item
    }

    // Método para actualizar el adaptador con nuevas valoraciones
    fun updateItems(newItems: List<ItemData_Valoraciones>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()  // Notifica que la lista ha cambiado
    }
}