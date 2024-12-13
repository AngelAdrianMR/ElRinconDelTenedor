package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.elrincondeltenedor.databinding.RestaurantDetailScreenBinding

class DetailsFragment : Fragment() {

    private var _binding: RestaurantDetailScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var restaurantViewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RestaurantDetailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el ViewModel
        restaurantViewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)

        // Obtener los datos del restaurante desde los argumentos
        val restaurantData = arguments?.getParcelable<ItemData_Collection>("restaurant_data")

        if (restaurantData != null) {
            bindRestaurantData(restaurantData)
        } else {
            Toast.makeText(context, "Datos del restaurante no disponibles", Toast.LENGTH_SHORT).show()
        }

        // Botón Guardar
        binding.btnGuardar.setOnClickListener {
            Toast.makeText(context, "Restaurante guardado", Toast.LENGTH_SHORT).show()
        }

        // Botón Valorar
        binding.btnValorar.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("restaurant_data", restaurantData)
            }
            findNavController().navigate(R.id.action_detailFragment_to_valoracionesFragment, bundle)
        }
    }

    /**
     * Método para asignar los datos del restaurante a las vistas
     */
    private fun bindRestaurantData(restaurant: ItemData_Collection) {
        binding.nombreRest.text = restaurant.text
        binding.descripcionRest.text = restaurant.description
        binding.imagenRest.setImageResource(restaurant.imageResId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
