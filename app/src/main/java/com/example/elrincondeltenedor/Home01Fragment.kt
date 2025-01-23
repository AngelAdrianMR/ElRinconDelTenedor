package com.example.elrincondeltenedor

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        // Recuperamos el estado del menú (si ya está desbloqueado)
        val sharedPreferences = requireContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

        binding.botonHome02.setOnClickListener{
            val myButton = view.findViewById<Button>(R.id.botonHome02)
            animateButtonPulse(myButton)
        }
    }

    private fun animateButtonPulse(button: View) {
        val scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 1.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 1.2f, 1f)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 500
        animatorSet.start()
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

}
