package com.example.elrincondeltenedor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
    private lateinit var collectionAdapter: RecyclerViewAdapter_Collection
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
        setupRecyclerView()

        // Escucha cambios en la colección "restaurantes_favoritos"
        listenToFavoritesUpdates()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCollection.layoutManager = LinearLayoutManager(context)
        collectionAdapter = RecyclerViewAdapter_Collection(mutableListOf()) { item ->
            val bundle = Bundle()
            bundle.putSerializable("restaurant_data", item)
            findNavController().navigate(R.id.action_collectionFragment_to_detailFragment, bundle)
        }
        binding.recyclerViewCollection.adapter = collectionAdapter
    }

    private fun listenToFavoritesUpdates() {
        listenerRegistration = firestore.collection("restaurantes_favoritos")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val favoritesList = snapshot.documents.mapNotNull { doc ->
                        try {
                            val name = doc.getString("name") ?: return@mapNotNull null
                            val description = doc.getString("description") ?: ""
                            val imageResId = doc.getString("imageResId") ?: "" // Leer el enlace

                            ItemData(name, imageResId, description)
                        } catch (e: Exception) {
                            Log.e("MappingError", "Error al mapear el documento: ${doc.id}", e)
                            null
                        }
                    }

                    collectionAdapter.updateData(favoritesList)

                    binding.textEmpty.visibility =
                        if (favoritesList.isEmpty()) View.VISIBLE else View.GONE
                } else {
                    binding.textEmpty.visibility = View.VISIBLE
                    Log.d("CollectionFragment", "No hay datos en Firestore.")
                }
            }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listenerRegistration?.remove() // Detiene la escucha de Firestore
    }
}
