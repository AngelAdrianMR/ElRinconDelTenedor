package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.CollectionScreenBinding

class CollectionFragment : Fragment() {

    private var _binding: CollectionScreenBinding? = null
    private val binding get() = _binding!!

    private var restaurantList: MutableList<ItemData> = mutableListOf()
    private lateinit var restaurantViewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectionScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el ViewModel
        restaurantViewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)

        // Recibe los datos del restaurante desde los argumentos (si los hay)
        val newRestaurant = arguments?.getSerializable("restaurant_data") as? ItemData
        newRestaurant?.let {
            // Verifica si el restaurante ya existe en el ViewModel antes de agregarlo
            if (restaurantViewModel.getRestaurantByName(it.name) == null) {
                restaurantViewModel.addRestaurant(it) // Si no existe, lo agregamos
            }
        }

        // Cargar la lista de restaurantes desde el ViewModel
        restaurantList.clear()
        restaurantList.addAll(restaurantViewModel.getAllRestaurants())

        // Configura el RecyclerView
        binding.recyclerViewCollection.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewCollection.adapter = RecyclerViewAdapter_Collection(restaurantList) { restaurant ->
            // Este bloque se ejecutará cuando se haga clic en un restaurante
            val bundle = Bundle()
            bundle.putSerializable("restaurant_data", restaurant)  // Pasa los datos del restaurante

            // Navega al DetailsFragment con los datos del restaurante
            findNavController().navigate(R.id.action_collectionFragment_to_detailFragment, bundle)
        }

        // Controla la visibilidad del mensaje vacío
        binding.textEmpty.visibility = if (restaurantList.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
