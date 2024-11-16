package com.example.elrincondeltenedor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.elrincondeltenedor.databinding.SettingScreenBinding

class SettingFragment : Fragment() {
    private var _binding: SettingScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SettingScreenBinding.inflate(inflater, container, false)

        // Inicializar SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Configurar CheckBox de notificaciones y ubicación
        setupCheckBox(binding.checkBoxNotificaciones, "notificaciones")
        setupCheckBox(binding.checkBoxUbicacion, "ubicacion")

        return binding.root
    }

    private fun setupCheckBox(checkBox: CheckBox, key: String) {
        // Cargar el estado guardado
        val isChecked = sharedPreferences.getBoolean(key, false)
        checkBox.isChecked = isChecked

        // Guardar el estado al cambiar
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(key, isChecked).apply()
            showToast("${checkBox.text} ${if (isChecked) "activado" else "desactivado"}")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
