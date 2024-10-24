package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.ValoracionesRecyclerviewBinding

class ValoracionesFragment : Fragment() {

    private var _binding: ValoracionesRecyclerviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ValoracionesRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView
        binding.recyclerViewRestaurant.layoutManager = LinearLayoutManager(context)
        val items = listOf(
            ItemData("Usuario 1", R.drawable.logo),
            // Agrega más elementos según sea necesario
        )

        binding.recyclerViewRestaurant.adapter = RecyclerViewAdapter_Valoraciones(items)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}