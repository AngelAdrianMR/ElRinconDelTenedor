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

        // Recibe el restaurante de los argumentos
        val restaurantData = arguments?.getSerializable("restaurant_data") as? ItemData
        restaurantData?.let {
            // Asigna los datos de ItemData a las vistas
            binding.nombreRest.text = it.name
            binding.descripcionRest.text = it.description
            binding.imagenRest.setImageResource(it.imageResId)
        }

        // Botón Guardar: Guarda el restaurante en el ViewModel
        binding.btnGuardar.setOnClickListener {
            restaurantData?.let { data ->
                restaurantViewModel.addRestaurant(data) // Guarda el restaurante en el ViewModel
                Toast.makeText(context, "Restaurante guardado", Toast.LENGTH_SHORT).show()

                // Opcional: Puedes hacer algo adicional si es necesario después de guardar el restaurante
            }
        }

        // Botón Valorar: Navega al ValoracionesFragment
        binding.btnValorar.setOnClickListener {
            restaurantData?.let { data ->
                val bundle = Bundle().apply {
                    putSerializable("restaurant_data", data)
                }
                // Navega al fragmento de valoraciones con los datos del restaurante
                findNavController().navigate(R.id.action_detailFragment_to_valoracionesFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
