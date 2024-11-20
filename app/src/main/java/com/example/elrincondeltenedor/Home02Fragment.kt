package com.example.elrincondeltenedor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.ScreenHome02Binding
import kotlinx.coroutines.launch

class Home02Fragment : Fragment() {

    private var _binding: ScreenHome02Binding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RecyclerViewAdapter_Home02

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenHome02Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el RecyclerView y configura el layout manager
        binding.recyclerViewHome02.layoutManager = LinearLayoutManager(requireContext())

        // Carga los datos del restaurante de forma asincrónica
        lifecycleScope.launch {
            loadRestaurant()
        }

        // Configura el botón de navegación a Home01Fragment
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

    private suspend fun loadRestaurant() {
        val items = listOf(
            ItemData(
                "Cele",
                R.drawable.cele,
                getString(R.string.descripcion01),
                valoraciones = mutableListOf(
                    ItemData_Valoraciones("Juan", getString(R.string.valoracion01)),
                    ItemData_Valoraciones("Ana", getString(R.string.valoracion02)),
                    ItemData_Valoraciones("Carlos", "Las tapas son deliciosas."),
                    ItemData_Valoraciones("Laura", "Muy buen servicio y calidad."),
                    ItemData_Valoraciones("David", "Repetiré sin duda.")
                )
            ),
            ItemData(
                "La Gata",
                R.drawable.lagata,
                getString(R.string.descripcion02),
                valoraciones = mutableListOf(
                    ItemData_Valoraciones("Marta", getString(R.string.valoracion03)),
                    ItemData_Valoraciones("Pedro", getString(R.string.valoracion04)),
                    ItemData_Valoraciones("Sofia", "La comida es excelente, pero algo cara."),
                    ItemData_Valoraciones("Miguel", "Un lugar perfecto para veganos."),
                    ItemData_Valoraciones("Bea", "Todo muy sabroso y de calidad.")
                )
            ),
            ItemData(
                "JMII",
                R.drawable.jm,
                getString(R.string.descripcion03),
                valoraciones = mutableListOf(
                    ItemData_Valoraciones("Lucas", getString(R.string.valoracion05)),
                    ItemData_Valoraciones("Raúl", getString(R.string.valoracion06)),
                    ItemData_Valoraciones("Olga", "Recomiendo las empanadas, muy buenas."),
                    ItemData_Valoraciones("Javier", "Un lugar con un ambiente rústico perfecto."),
                    ItemData_Valoraciones("Esther", "La mariscada es exquisita.")
                )
            ),
            ItemData(
                "Chiringuito Nido Playa",
                R.drawable.nidoplaya,
                getString(R.string.descripcion04),
                valoraciones = mutableListOf(
                    ItemData_Valoraciones("Carlos", getString(R.string.valoracion07)),
                    ItemData_Valoraciones("Lucía", getString(R.string.valoracion08)),
                    ItemData_Valoraciones("Paula", "¡Las gambas a la plancha están deliciosas!"),
                    ItemData_Valoraciones("Fernando", "Las vistas son increíbles y la comida mejor."),
                    ItemData_Valoraciones("Celia", "El arroz con mariscos es espectacular.")
                )
            ),
            ItemData(
                "Asador LHUMUS",
                R.drawable.lhumus,
                getString(R.string.descripcion05),
                valoraciones = mutableListOf(
                    ItemData_Valoraciones("Julio", getString(R.string.valoracion09)),
                    ItemData_Valoraciones("Sandra", getString(R.string.valoracion10)),
                    ItemData_Valoraciones("Cristina", "Me encanta su variedad de vinos."),
                    ItemData_Valoraciones("Jorge", "Perfecto para una cena de calidad."),
                    ItemData_Valoraciones("Raquel", "Los platos son generosos y sabrosos.")
                )
            ),
            ItemData(
                "Restaurante Cantón",
                R.drawable.canton,
                getString(R.string.descripcion06),
                valoraciones = mutableListOf(
                    ItemData_Valoraciones("Nerea", "Un lugar ideal para sushi."),
                    ItemData_Valoraciones("Mónica", "Los dim sum son deliciosos."),
                    ItemData_Valoraciones("Pablo", "Excelente comida asiática."),
                    ItemData_Valoraciones("David", "Me encanta la variedad que ofrecen."),
                    ItemData_Valoraciones("Sofía", "Súper recomendable para los amantes de la comida china.")
                )
            ),
            ItemData(
                "Pizzería Aless&Patricia",
                R.drawable.ales,
                getString(R.string.descripcion07),
                valoraciones = mutableListOf(
                    ItemData_Valoraciones("Ana", "Las pizzas son deliciosas, lo mejor de la zona."),
                    ItemData_Valoraciones("Juan", "La pasta es excelente, se nota que es casera."),
                    ItemData_Valoraciones("Carlos", "Buen ambiente, ideal para ir con familia."),
                    ItemData_Valoraciones("Marta", "El tiramisú es el mejor que he probado."),
                    ItemData_Valoraciones("Antonio", "Ideal para una noche de pizza.")
                )
            ),
            ItemData(
                "Brasserie Panini",
                R.drawable.braseria,
                getString(R.string.descripcion08),
                valoraciones = mutableListOf(
                    ItemData_Valoraciones("Laura", "Un lugar diferente con muy buena comida."),
                    ItemData_Valoraciones("Bea", "La cerveza holandesa y las patatas fritas son excelentes."),
                    ItemData_Valoraciones("Carlos", "Me sorprendió gratamente la comida."),
                    ItemData_Valoraciones("Sofía", "Muy auténtico, con un ambiente acogedor."),
                    ItemData_Valoraciones("Miguel", "Las ensaladas son muy frescas y sabrosas.")
                )
            ),
            ItemData(
                "La Barraca",
                R.drawable.barraca,
                getString(R.string.descripcion09),
                valoraciones = mutableListOf(
                    ItemData_Valoraciones("Ricardo", "El arroz a la marinera está delicioso."),
                    ItemData_Valoraciones("Ana", "Comida excelente con vistas preciosas."),
                    ItemData_Valoraciones("Manuel", "El marisco es fresco y muy sabroso."),
                    ItemData_Valoraciones("Carmen", "La terraza es espectacular, y la comida muy buena."),
                    ItemData_Valoraciones("Carlos", "Muy recomendable para los amantes del pescado.")
                )
            ),
            ItemData(
                "Bodega Patio Salao",
                R.drawable.patiosalao,
                getString(R.string.descripcion10),
                valoraciones = mutableListOf(
                    ItemData_Valoraciones("Pablo", "Todo muy sabroso, pero las tapas son lo mejor."),
                    ItemData_Valoraciones("Sofía", "El ambiente es muy relajante."),
                    ItemData_Valoraciones("Javier", "Recomiendo las fuentes de embutidos."),
                    ItemData_Valoraciones("Juan", "Un restaurante con encanto y buena comida."),
                    ItemData_Valoraciones("Raúl", "La parrillada de pescado es fenomenal.")
                )
            ),
            ItemData(
                "La Tagliatella",
                R.drawable.tagliateella,
                getString(R.string.descripcion11),
                valoraciones = mutableListOf(
                    ItemData_Valoraciones("Claudia", "La comida italiana auténtica, me encanta."),
                    ItemData_Valoraciones("Raquel", "Perfecto para una cena tranquila con amigos."),
                    ItemData_Valoraciones("Fernando", "Las pastas son sabrosísimas."),
                    ItemData_Valoraciones("Juan", "Es uno de mis restaurantes italianos favoritos."),
                    ItemData_Valoraciones("Sonia", "Muy buena opción para los amantes de la pizza.")
                )
            )
        )

        setupRecyclerView(items)
    }


    private fun setupRecyclerView(restaurants: List<ItemData>) {
        if (restaurants.isNotEmpty()) {
            adapter = RecyclerViewAdapter_Home02(restaurants, ::onRestaurantClicked)
            binding.recyclerViewHome02.adapter = adapter
        } else {
            // Si no hay datos, muestra un mensaje
            Log.e("RecyclerView", "No data available for the restaurant list.")
        }
    }

    private fun onRestaurantClicked(itemData: ItemData) {
        // Crea el Bundle y pasa el ItemData a detailFragment
        val bundle = Bundle().apply {
            putSerializable("restaurant_data", itemData)
        }
        findNavController().navigate(R.id.detailFragment, bundle)
    }
}