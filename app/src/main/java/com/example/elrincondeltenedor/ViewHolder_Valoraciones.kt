package com.example.elrincondeltenedor

import androidx.recyclerview.widget.RecyclerView
import com.example.elrincondeltenedor.databinding.ItemCardviewValoracionesBinding

class ViewHolder_Valoraciones(private val binding: ItemCardviewValoracionesBinding) : RecyclerView.ViewHolder(binding.root) {

    // Modificado el método 'bind' para mostrar 'Usuario: valoracion' en lugar de solo el 'item'
    fun bind(item: ItemData_Valoraciones) {
        // Concatenamos 'usuario' y 'valoracion' en el formato requerido y asignamos el texto
        binding.textViewValoracion.text = "${item.usuario} : ${item.valoracion}"
        binding.executePendingBindings()
    }
}