package com.example.elrincondeltenedor

import android.os.Bundle
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

        // Observa los cambios en Firestore
        listenToCollectionUpdates()

        // Verifica si se pasan datos de una nueva colección
        val newCollection = arguments?.getParcelable<ItemData_Collection>("restaurant_data")
        newCollection?.let {
            addCollectionToFirestore(it) // Agrega la nueva colección
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCollection.layoutManager = LinearLayoutManager(context)
        collectionAdapter = RecyclerViewAdapter_Collection(mutableListOf()) { collection ->
            val bundle = Bundle()
            bundle.putParcelable("restaurant_data", collection) // Asegúrate de usar el tipo correcto
            findNavController().navigate(R.id.action_collectionFragment_to_detailFragment, bundle)
        }
        binding.recyclerViewCollection.adapter = collectionAdapter
    }

    private fun addCollectionToFirestore(collection: ItemData_Collection) {
        val collectionData = hashMapOf(
            "text" to collection.text,
            "description" to collection.description,
            "imageResId" to collection.imageResId
        )
        firestore.collection("collections")
            .add(collectionData)
            .addOnSuccessListener {
                // Éxito al agregar
            }
            .addOnFailureListener { e ->
                e.printStackTrace() // Manejo del error
            }
    }

    private fun listenToCollectionUpdates() {
        listenerRegistration = firestore.collection("collections")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val collectionList = snapshot.documents.mapNotNull { doc ->
                        try {
                            doc.toObject(ItemData_Collection::class.java)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }
                    }
                    collectionAdapter.updateData(collectionList) // Usamos collectionList
                    binding.textEmpty.visibility =
                        if (collectionList.isEmpty()) View.VISIBLE else View.GONE
                } else {
                    binding.textEmpty.visibility = View.VISIBLE
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listenerRegistration?.remove() // Detiene la escucha de Firestore
    }
}
