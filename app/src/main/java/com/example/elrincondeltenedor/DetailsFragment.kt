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
import com.google.firebase.firestore.FirebaseFirestore

class DetailsFragment : Fragment() {

    private var _binding: RestaurantDetailScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var restaurantViewModel: ViewModel
    private val db = FirebaseFirestore.getInstance() // Instancia de Firestore

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

        // Obtener el ID del restaurante desde los argumentos
        val restaurantId = arguments?.getString("restaurant_id")

        // Si hay un ID de restaurante, cargar los datos de Firestore
        restaurantId?.let { id ->
            loadRestaurantData(id)
        }

        // Botón Guardar: Guarda el restaurante en el ViewModel
        binding.btnGuardar.setOnClickListener {
            // Aquí puedes agregar lógica para guardar el restaurante en tu ViewModel si es necesario
            Toast.makeText(context, "Restaurante guardado", Toast.LENGTH_SHORT).show()
        }

        // Botón Valorar: Navega al ValoracionesFragment
        binding.btnValorar.setOnClickListener {
            val bundle = Bundle().apply {
                putString("restaurant_id", restaurantId)
            }
            findNavController().navigate(R.id.action_detailFragment_to_valoracionesFragment, bundle)
        }
    }

    // Método para cargar los datos del restaurante desde Firestore
    private fun loadRestaurantData(restaurantId: String) {
        db.collection("restaurantes").document(restaurantId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val name = document.getString("name") ?: "Nombre no disponible"
                    val description = document.getString("description") ?: "Descripción no disponible"
                    val imageResId = document.getString("imageResId")?.toInt() ?: R.drawable.casa // Puedes usar un valor predeterminado
                    // Puedes añadir la lógica para las valoraciones aquí

                    // Asignar los datos a las vistas
                    binding.nombreRest.text = name
                    binding.descripcionRest.text = description
                    binding.imagenRest.setImageResource(imageResId)
                } else {
                    Toast.makeText(context, "Restaurante no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error al cargar los datos: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
