package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.ScreenHome02Binding

class Home02Fragment : Fragment() {

    private var _binding: ScreenHome02Binding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RecyclerViewAdapter_Home02
    private var dataList: List<ItemData> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Permite que el fragmento tenga su propio menú
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenHome02Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el RecyclerView
        binding.recyclerViewHome02.layoutManager = LinearLayoutManager(context)

        // Carga los datos
        dataList = loadData()

        // Inicializa el adaptador
        adapter = RecyclerViewAdapter_Home02(dataList)
        binding.recyclerViewHome02.adapter = adapter

        binding.botonHome01.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, Home01Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_options, menu) // Inflar el mismo menú que en Home01Fragment
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Lógica para abrir la configuración
                true
            }
            R.id.action_collection -> {
                // Lógica para manejar la acción de colección
                true
            }
            R.id.action_profile -> {
                // Lógica para abrir el perfil
                true
            }
            R.id.action_logout -> {
                // Lógica para cerrar sesión
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadData(): List<ItemData> {
        return listOf(
            ItemData("Item 1", R.drawable.casa),
            ItemData("Item 2", R.drawable.casa),
            ItemData("Item 3", R.drawable.casa)
        )
    }
}