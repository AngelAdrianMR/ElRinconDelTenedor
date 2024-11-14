package com.example.elrincondeltenedor

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.ValoracionesRecyclerviewBinding

class ValoracionesFragment : Fragment() {

    private var _binding: ValoracionesRecyclerviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentRestaurant: ItemData
    private lateinit var valoracionesAdapter: RecyclerViewAdapter_Valoraciones
    private lateinit var restaurantViewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ValoracionesRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el ViewModel
        restaurantViewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)

        // Recibe el restaurante de los argumentos
        currentRestaurant = arguments?.getSerializable("restaurant_data") as? ItemData
            ?: return

        // Verifica si el restaurante existe en el ViewModel
        currentRestaurant = restaurantViewModel.getRestaurantByName(currentRestaurant.name) ?: return

        // Configura el RecyclerView con las valoraciones del restaurante
        binding.recyclerViewRestaurant.layoutManager = LinearLayoutManager(context)
        valoracionesAdapter = RecyclerViewAdapter_Valoraciones(currentRestaurant.valoraciones)
        binding.recyclerViewRestaurant.adapter = valoracionesAdapter

        // Verifica si ya hay valoraciones y muestra un mensaje si no hay
        binding.textEmpty.visibility = if (currentRestaurant.valoraciones.isEmpty()) View.VISIBLE else View.GONE

        // Configura el botón de valoración
        binding.btnValorar.setOnClickListener {
            val valoracion = binding.editTextValoracion.text.toString().trim()

            if (valoracion.isNotEmpty()) {
                // Añade la nueva valoración a la lista de valoraciones del restaurante
                val nuevaValoracion = ItemData_Valoraciones("Usuario", valoracion)
                currentRestaurant.valoraciones.add(nuevaValoracion)

                // Actualiza el RecyclerView
                valoracionesAdapter.notifyDataSetChanged()

                // Limpia el campo de texto
                binding.editTextValoracion.text.clear()

                // Verifica si hay valoraciones y oculta el mensaje de "no tiene valoraciones"
                binding.textEmpty.visibility = if (currentRestaurant.valoraciones.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


