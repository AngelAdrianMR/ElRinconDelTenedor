package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.elrincondeltenedor.databinding.RestaurantDetailScreenBinding

class DetailsFragment : Fragment() {
    private var _binding: RestaurantDetailScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RestaurantDetailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true) // Agrega esto

        // Suponiendo que has recibido datos del Bundle
        val itemName = arguments?.getString("itemName") ?: "Nombre por defecto"
        val itemImage = arguments?.getInt("itemImage") ?: R.drawable.casa
        val itemDescription = "Descripción por defecto" // Cambia esto según tu lógica

        // Asignar datos a la vista
        binding.nombreRest.text = itemName
        binding.descripcionRest.text = itemDescription
        binding.imagenRest.setImageResource(itemImage)

        // Configurar el botón de valoración
        binding.btnGuardar.setOnClickListener {
            // Crear la lista con el restaurante
            val restaurantList = listOf(
                ItemData_Collection(itemName, itemDescription, itemImage)
            )

            // Navegar a CollectionFragment y pasar los datos
            val collectionFragment = CollectionFragment.newInstance(restaurantList)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, collectionFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.btnValorar.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, ValoracionesFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}