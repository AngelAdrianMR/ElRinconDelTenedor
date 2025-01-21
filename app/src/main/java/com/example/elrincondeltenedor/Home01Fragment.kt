package com.example.elrincondeltenedor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.elrincondeltenedor.databinding.HomeScreen01Binding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Home01Fragment : Fragment(R.layout.home_screen_01), OnMapReadyCallback {

    private var _binding: HomeScreen01Binding? = null
    private val binding get() = _binding!!

    private var isGuideActive = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeScreen01Binding.inflate(inflater, container, false)

        // Inicializar el mapa
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Comprobar si es la primera vez que se abre la app
        val sharedPreferences = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("is_first_launch", true)

        if (isFirstLaunch) {
            // Mostrar la guía si es la primera vez
            showGuide()
            sharedPreferences.edit().putBoolean("is_first_launch", false).apply()
        }

        // Configurar el botón que navega a la siguiente pantalla
        binding.botonHome02.setOnClickListener {
            if (!isGuideActive) {  // Verificar si la guía no está activa
                findNavController().navigate(R.id.home02Fragment)
            }
        }

        // Configurar el botón "Siguiente"
        binding.nextButton.setOnClickListener {
            // Cambiar texto de la guía
            binding.guideText.text = "Pulsa sobre el botón"
            binding.nextButton.visibility = View.GONE

            // Desbloquear el botón de home
            binding.botonHome02.isEnabled = true
            binding.botonHome02.setBackgroundColor(resources.getColor(R.color.bluecian)) // Cambiar color

            // Desactivar la guía
            deactivateGuide()
        }

        // Configurar el botón "Comenzar Guía"
        binding.startGuideButton.setOnClickListener {
            // Cambiar el texto de la guía
            binding.guideText.text = "Este botón te llevará a la lista de restaurantes. Pulsa sobre el."

            // Cambiar color del botón de home (solo color)
            binding.botonHome02.setBackgroundColor(resources.getColor(R.color.grey))

            // Mostrar botón "Siguiente" y ocultar el botón "Comenzar Guía"
            binding.nextButton.visibility = View.VISIBLE
            binding.startGuideButton.visibility = View.GONE
            binding.skipGuideButton.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latlng = LatLng(36.83, -2.45)
        googleMap.addMarker(MarkerOptions().position(latlng).title("Marcador"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15f))
    }

    // Método para mostrar la guía y bloquear la interacción
    private fun showGuide() {
        // Mostrar el overlay
        binding.overlay.visibility = View.VISIBLE

        // Bloquear los botones y el mapa
        binding.botonHome02.isEnabled = false
        binding.mapView.isClickable = false

        // Activar la guía
        isGuideActive = true
    }

    // Método para desactivar la guía y restaurar la interacción
    private fun deactivateGuide() {
        // Ocultar el overlay
        binding.overlay.visibility = View.GONE

        // Restaurar la interacción con los botones y mapa
        binding.mapView.isClickable = true
        isGuideActive = false
    }
}
