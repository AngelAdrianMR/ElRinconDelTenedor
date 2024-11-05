package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.CollectionScreenBinding

class CollectionFragment : Fragment() {
    private var _binding: CollectionScreenBinding? = null
    private val binding get() = _binding!!

    // Lista mutable para almacenar los restaurantes
    private var restaurantList: MutableList<ItemData_Collection> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectionScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Si hay datos en los argumentos, agrega esos restaurantes
        val newRestaurants = arguments?.getParcelableArrayList<ItemData_Collection>("restaurantList")
        if (newRestaurants != null) {
            restaurantList.addAll(newRestaurants)
        }

        // Configura el RecyclerView
        binding.recyclerViewCollection.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewCollection.adapter = RecyclerViewAdapter_Collection(restaurantList)

        // Maneja la visibilidad del mensaje vacío
        binding.textEmpty.visibility = if (restaurantList.isEmpty()) View.VISIBLE else View.GONE

        // Asegúrate de que el menú se actualice si es necesario
        requireActivity().invalidateOptionsMenu()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(restaurantList: List<ItemData_Collection>): CollectionFragment {
            val fragment = CollectionFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("restaurantList", ArrayList(restaurantList))
            fragment.arguments = bundle
            return fragment
        }
    }
}