package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
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