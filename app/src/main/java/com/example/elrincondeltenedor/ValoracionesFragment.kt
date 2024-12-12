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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ValoracionesFragment : Fragment() {

    private var _binding: ValoracionesRecyclerviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentRestaurant: ItemData
    private lateinit var valoracionesAdapter: RecyclerViewAdapter_Valoraciones
    private lateinit var restaurantViewModel: ViewModel
    private val db = FirebaseFirestore.getInstance() // Firebase Firestore instance

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
                // Obtener el nombre del usuario autenticado
                val user = Firebase.auth.currentUser
                val username = user?.displayName ?: "Usuario desconocido" // Si el nombre es nulo, asignar "Usuario desconocido"

                // Crear una nueva valoración con el nombre real del usuario
                val nuevaValoracion = ItemData_Valoraciones(username, valoracion)

                // Guardar la valoración en Firebase Firestore
                saveValoracionToFirestore(currentRestaurant.name, nuevaValoracion)

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
        obtenerValoracionesDeFirebase(currentRestaurant.name)
    }

    // Función para actualizar el RecyclerView con las valoraciones obtenidas
    private fun actualizarValoraciones(valoraciones: List<ItemData_Valoraciones>) {
        // Actualizar el adaptador con las nuevas valoraciones
        valoracionesAdapter.updateItems(valoraciones)

        // Si no hay valoraciones, mostrar el mensaje de "no hay valoraciones"
        binding.textEmpty.visibility = if (valoraciones.isEmpty()) View.VISIBLE else View.GONE
    }

    // Función para guardar la valoración en Firestore
    private fun saveValoracionToFirestore(restaurantName: String, nuevaValoracion: ItemData_Valoraciones) {
        // Guarda la valoración en la subcolección "valoraciones" de un restaurante
        val restaurantRef = db.collection("restaurantes").document(restaurantName)
            .collection("valoraciones")

        // Agregar la valoración como un nuevo documento en Firestore
        restaurantRef.add(nuevaValoracion)
            .addOnSuccessListener {
                // Actualizamos el RecyclerView con las nuevas valoraciones
                obtenerValoracionesDeFirebase(restaurantName)

                // Limpiar el campo de texto de la valoración
                binding.editTextValoracion.text.clear()

                // Ocultar el mensaje "no hay valoraciones"
                binding.textEmpty.visibility = View.GONE

                Toast.makeText(context, "Valoración guardada exitosamente.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al guardar la valoración: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Función para obtener las valoraciones del restaurante desde Firestore
    private fun obtenerValoracionesDeFirebase(restaurantName: String) {
        // Obtener las valoraciones de la subcolección "valoraciones" de un restaurante específico
        db.collection("restaurantes")
            .document(restaurantName)
            .collection("valoraciones")
            .get()
            .addOnSuccessListener { result ->
                val valoraciones = mutableListOf<ItemData_Valoraciones>()

                // Iterar sobre los documentos de la subcolección "valoraciones"
                for (document in result) {
                    val usuario = document.getString("usuario") ?: "Desconocido"
                    val valoracion = document.getString("valoracion") ?: "Sin valoración"

                    // Crear un objeto de tipo ItemData_Valoraciones y agregarlo a la lista
                    val item = ItemData_Valoraciones(usuario, valoracion)
                    valoraciones.add(item)
                }

                // Una vez que tengamos las valoraciones, actualizar el RecyclerView
                actualizarValoraciones(valoraciones)
            }
            .addOnFailureListener { exception ->
                // Si ocurre un error al obtener las valoraciones
                Toast.makeText(context, "Error al cargar las valoraciones: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
