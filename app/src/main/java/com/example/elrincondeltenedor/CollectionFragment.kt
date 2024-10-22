package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.CollectionScreenBinding

class CollectionFragment : Fragment() {
    private var _binding: CollectionScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectionScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el RecyclerView
        binding.recyclerViewCollection.layoutManager = LinearLayoutManager(context)
        val items = listOf(
            ItemData("1", R.drawable.logo)
        )
        binding.recyclerViewCollection.adapter = RecyclerViewAdapter_Collection(items)

        // Habilitar el menú de opciones directamente en el fragmento
        requireActivity().invalidateOptionsMenu()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}