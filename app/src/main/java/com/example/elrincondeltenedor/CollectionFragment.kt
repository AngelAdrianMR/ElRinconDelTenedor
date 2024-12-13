package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.CollectionScreenBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CollectionFragment : Fragment() {

    private var _binding: CollectionScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var firestore: FirebaseFirestore
    private lateinit var restaurantAdapter: RecyclerViewAdapter_Collection
    private var listenerRegistration: ListenerRegistration? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectionScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa Firestore
        firestore = Firebase.firestore

        // Configura el RecyclerView
        binding.recyclerViewCollection.layoutManager = LinearLayoutManager(context)
        restaurantAdapter = RecyclerViewAdapter_Collection(mutableListOf()) { restaurant ->
            // Navega al DetailsFragment con los datos del restaurante seleccionado
            val bundle = Bundle()
            bundle.putParcelable("restaurant_data", restaurant)
            findNavController(detailFragment)
        }
        binding.recyclerViewCollection.adapter = restaurantAdapter

        // Observa los cambios en Firestore
        listenToRestaurantUpdates()

        // Verifica si se pasan datos de un nuevo restaurante
        val newRestaurant = arguments?.getParcelable<ItemData_Collection>("restaurant_data")
        newRestaurant?.let {
            addRestaurantToFirestore(it) // Agrega el nuevo restaurante
        }
    }

    /**
     * Agrega un restaurante a Firestore.
     */
    private fun addRestaurantToFirestore(restaurant: ItemData_Collection) {
        val restaurantData = hashMapOf(
            "text" to restaurant.text,
            "description" to restaurant.description,
            "imageResId" to restaurant.imageResId
        )
        firestore.collection("restaurants")
            .add(restaurantData)
            .addOnSuccessListener {
                // Éxito al agregar
            }
            .addOnFailureListener { e ->
                // Manejo del error
                e.printStackTrace()
            }
    }

    /**
     * Escucha las actualizaciones de Firestore y actualiza el RecyclerView.
     */
    private fun listenToRestaurantUpdates() {
        listenerRegistration = firestore.collection("restaurants")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    error?.printStackTrace()
                    return@addSnapshotListener
                }
                // Convierte los datos de Firestore a objetos ItemData_Collection
                val restaurantList = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(ItemData_Collection::class.java)
                }
                restaurantAdapter.updateData(restaurantList)

                // Controla la visibilidad del mensaje de lista vacía
                binding.textEmpty.visibility = if (restaurantList.isEmpty()) View.VISIBLE else View.GONE
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listenerRegistration?.remove() // Detiene la escucha de Firestore
    }
}
