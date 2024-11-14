package com.example.elrincondeltenedor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.ScreenHome02Binding
import kotlinx.coroutines.launch

class Home02Fragment : Fragment() {

    private var _binding: ScreenHome02Binding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RecyclerViewAdapter_Home02

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenHome02Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el RecyclerView y configura el layout manager
        binding.recyclerViewHome02.layoutManager = LinearLayoutManager(requireContext())

        // Carga los datos del restaurante de forma asincrónica
        lifecycleScope.launch {
            loadRestaurant()
        }

        // Configura el botón de navegación a Home01Fragment
        binding.botonHome01.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, Home01Fragment())
                .addToBackStack(null)
                .commit()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Carga asincrónica de datos y configuración del RecyclerView
    private suspend fun loadRestaurant() {
        val items = listOf(
            ItemData("Cele", R.drawable.casa, "Las mejores tapas"),
            ItemData("La Gata", R.drawable.casa, "Las mejores tapas Veganas"),
            ItemData("JMII", R.drawable.casa, "Estas no son tan buenas")
        )

        // Configura el RecyclerView con los datos cargados
        setupRecyclerView(items)
    }

    private fun setupRecyclerView(restaurants: List<ItemData>) {
        if (restaurants.isNotEmpty()) {
            adapter = RecyclerViewAdapter_Home02(restaurants, ::onRestaurantClicked)
            binding.recyclerViewHome02.adapter = adapter
        } else {
            // Si no hay datos, muestra un mensaje
            Log.e("RecyclerView", "No data available for the restaurant list.")
        }
    }

    private fun onRestaurantClicked(itemData: ItemData) {
        // Crea el Bundle y pasa el ItemData a detailFragment
        val bundle = Bundle().apply {
            putSerializable("restaurant_data", itemData)
        }
        findNavController().navigate(R.id.detailFragment, bundle)
    }
}