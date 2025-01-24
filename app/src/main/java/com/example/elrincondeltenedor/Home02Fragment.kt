package com.example.elrincondeltenedor

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        // Configuración del RecyclerView
        binding.recyclerViewHome02.layoutManager = LinearLayoutManager(requireContext())

        // Cargar datos desde Firestore
        lifecycleScope.launch {
            loadRestaurant()
        }

        // Animacion del boton
        binding.botoAHome01.setOnClickListener {
            val boton = view.findViewById<Button>(R.id.botoAHome01)
            animateButtonPulse(boton)        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun animateButtonPulse(button: View) {
        val scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 1.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 1.2f, 1f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 500
        animatorSet.start()
    }

    private suspend fun loadRestaurant() {
        val db = FirebaseFirestore.getInstance()

        try {
            val restaurantCollection = db.collection("restaurantes")
            val snapshot = restaurantCollection.get().await()

            val restaurants = snapshot.documents.mapNotNull { document ->
                val name = document.getString("name")
                val description = document.getString("description") ?: ""
                val imageUrl = document.getString("imageResId")

                if (name != null) {
                    Log.d("Firestore", "Loaded: $name, $description, $imageUrl")
                    ItemData(name, imageUrl ?: "", description)
                } else {
                    Log.e("Firestore", "Missing required fields in document: ${document.id}")
                    null
                }
            }

            // Configurar el RecyclerView con los datos obtenidos
            setupRecyclerView(restaurants)

        } catch (e: Exception) {
            Log.e("Firestore", "Error loading restaurants: ${e.message}", e)
        }
    }


    private fun setupRecyclerView(restaurants: List<ItemData>) {
        if (restaurants.isNotEmpty()) {
            adapter = RecyclerViewAdapter_Home02(restaurants, ::onRestaurantClicked)
            binding.recyclerViewHome02.adapter = adapter
        } else {
            Log.e("RecyclerView", "No data available for the restaurant list.")
        }
    }

    private fun onRestaurantClicked(itemData: ItemData) {
        val bundle = Bundle().apply {
            putSerializable("restaurant_data", itemData)
        }
        findNavController().navigate(R.id.detailFragment, bundle)
    }
}
