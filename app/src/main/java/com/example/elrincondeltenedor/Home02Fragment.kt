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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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

        // Carga los datos desde Firestore de forma asincrónica
        lifecycleScope.launch {
            loadRestaurant()
        }

        // Configura el botón de navegación a Home01Fragment
        binding.botonHome01.setOnClickListener {
            findNavController().navigate(R.id.action_home02Fragment_to_home01Fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun loadRestaurant() {
        val db = FirebaseFirestore.getInstance()

        // Obtener todos los restaurantes desde Firestore
        val restaurantCollection = db.collection("restaurantes")
        val snapshot = restaurantCollection.get().await()

        val restaurants = snapshot.documents.mapNotNull { document ->
            val name = document.getString("name") ?: return@mapNotNull null
            val description = document.getString("description") ?: ""
            val imageResId = R.drawable.casa // Coloca una imagen por defecto mientras se recupera la URL de la imagen
            ItemData(
                name,
                imageResId,
                description
            )
        }

        setupRecyclerView(restaurants)
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