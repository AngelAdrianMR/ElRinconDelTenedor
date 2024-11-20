package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        // Inicializamos el RecyclerView con una lista vacía de valoraciones
        valoracionesAdapter = RecyclerViewAdapter_Valoraciones(mutableListOf())
        binding.recyclerViewRestaurant.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewRestaurant.adapter = valoracionesAdapter

        // Configura el botón de "valórame"
        binding.btnValorame.setOnClickListener {
            val valoracion = binding.editTextValoracion.text.toString().trim()
            if (valoracion.isNotEmpty()) {
                // Crear una nueva valoración
                val nuevaValoracion = ItemData_Valoraciones("Usuario", valoracion)

                // Agregar la valoración al ViewModel
                restaurantViewModel.addValoracionToRestaurant(currentRestaurant.name, nuevaValoracion)

                // Actualizar el RecyclerView con las valoraciones
                actualizarValoraciones()

                // Limpiar el campo de texto
                binding.editTextValoracion.text.clear()

                // Ocultar el mensaje de "no hay valoraciones"
                binding.textEmpty.visibility = View.GONE
            } else {
                // Si el campo de texto está vacío, mostrar un mensaje de error
                Toast.makeText(context, "Por favor, escribe una valoración.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el ViewModel
        restaurantViewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)

        // Recibir el restaurante desde los argumentos
        currentRestaurant = arguments?.getSerializable("restaurant_data") as? ItemData
            ?: return

        // Inicializar el RecyclerView con las valoraciones actuales
        actualizarValoraciones()
    }

    // Función para actualizar el RecyclerView con las valoraciones
    private fun actualizarValoraciones() {
        // Obtener las valoraciones del restaurante desde el ViewModel
        val valoraciones = restaurantViewModel.getValoracionesForRestaurant(currentRestaurant.name)

        // Actualizar el adaptador con las valoraciones obtenidas
        valoracionesAdapter.updateItems(valoraciones)

        // Si no hay valoraciones, mostrar el mensaje de "no hay valoraciones"
        binding.textEmpty.visibility = if (valoraciones.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}