package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.elrincondeltenedor.databinding.RestaurantDetailScreenBinding

class DetailsFragment : Fragment() {
    private lateinit var _binding: RestaurantDetailScreenBinding
    private val binding get() = _binding

    private val restaurantList = mutableListOf<ItemData_Collection>() // Lista para almacenar los restaurantes

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RestaurantDetailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnGuardar.setOnClickListener {
            val restaurantName = binding.nombreRest.text.toString()
            val restaurantDescription = binding.descripcionRest.text.toString()
            val restaurantImage = R.drawable.casa // Cambia según tu lógica para imágenes

            // Guarda el restaurante en la lista como ItemData_Collection
            restaurantList.add(ItemData_Collection(restaurantName, restaurantDescription, restaurantImage))

            Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_collectionFragment)

        }

        binding.btnValorar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_valoracionesFragment)
        }
    }
}