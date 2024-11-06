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

        // Carga los datos del restaurante al crear la vista
        loadRestaurant()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el RecyclerView y configura el layout manager
        binding.recyclerViewHome02.layoutManager = LinearLayoutManager(requireContext())

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

    private fun loadRestaurant() {
        lifecycleScope.launch {
            try {
                // Llamada a la API para obtener los restaurantes
                val response = RetrofitInstance.api.getRestaurant()

                // Verificar si la respuesta es exitosa
                if (response.isSuccessful) {
                    response.body()?.let { itemResponse ->
                        // Configura el RecyclerView con la colección obtenida de la API
                        setupRecyclerView(itemResponse.restaurant)
                    }

                } else {
                    // Si la respuesta no es exitosa, mostrar el error
                    Log.e("API Error", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                // Si ocurre una excepción en la solicitud, se captura aquí
                Log.e("Network Error", "Exception: $e")
            }
        }
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