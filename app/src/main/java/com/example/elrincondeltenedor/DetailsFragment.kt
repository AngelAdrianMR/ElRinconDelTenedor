package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.elrincondeltenedor.databinding.RestaurantDetailScreenBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

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

        // Obtener los datos del restaurante desde los argumentos
        val restaurantData = arguments?.getSerializable("restaurant_data") as? ItemData

        if (restaurantData != null) {
            bindRestaurantData(restaurantData)
        } else {
            Toast.makeText(context, "Datos del restaurante no disponibles", Toast.LENGTH_SHORT).show()
        }


        binding.btnGuardar.setOnClickListener {
            restaurantData?.let { data ->
                saveRestaurantToFavorites(data)
            }
        }

        binding.btnValorar.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("restaurant_data", restaurantData)
            }
            findNavController().navigate(R.id.action_detailFragment_to_valoracionesFragment, bundle)
        }
    }

    private fun saveRestaurantToFavorites(restaurant: ItemData) {
        val db = FirebaseFirestore.getInstance()

        // Verificar si el restaurante ya está en favoritos
        val restaurantCollection = db.collection("restaurantes_favoritos")
        val query = restaurantCollection.whereEqualTo("name", restaurant.name)

        query.get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {
                    // Si el restaurante ya está en favoritos
                    Toast.makeText(context, "Este restaurante ya está en favoritos", Toast.LENGTH_SHORT).show()
                } else {
                    // Si no está en favoritos, agregarlo
                    val favoriteData = hashMapOf(
                        "name" to restaurant.name,
                        "description" to restaurant.description,
                        "imageResId" to restaurant.imageResId
                    )

                    restaurantCollection.add(favoriteData)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Restaurante guardado en favoritos", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            e.printStackTrace()
                            Toast.makeText(context, "Error al guardar restaurante", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                Toast.makeText(context, "Error al verificar si el restaurante está en favoritos", Toast.LENGTH_SHORT).show()
            }
    }


    private fun bindRestaurantData(restaurant: ItemData) {
        binding.nombreRest.text = restaurant.name
        binding.descripcionRest.text = restaurant.description

        // Cargar la imagen usando Picasso
        Picasso.get()
            .load(restaurant.imageResId) // URL de la imagen desde Firestore
            .placeholder(R.drawable.ic_launcher_background) // Imagen temporal mientras carga
            .error(R.drawable.casa) // Imagen de respaldo en caso de error
            .into(binding.imagenRest)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
