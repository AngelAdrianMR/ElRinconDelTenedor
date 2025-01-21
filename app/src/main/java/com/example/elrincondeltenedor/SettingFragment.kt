package com.example.elrincondeltenedor

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.elrincondeltenedor.databinding.SettingScreenBinding
import java.util.*

class SettingFragment : Fragment() {
    private var _binding: SettingScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    // Lista de idiomas
    private val languageCodes = arrayOf("en", "es", "fr") // Añade más idiomas si es necesario
    private val languageNames = arrayOf("English", "Español")

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

        // Configurar el Spinner de idioma
        setupLanguageSpinner()

        // Botón de guardar
        binding.btnSave.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        // Verificar si es la primera vez que se accede a esta pantalla
        val savedIsFirstTime = sharedPreferences.getBoolean("isFirstTimeSettings", true)

        if (savedIsFirstTime) {
            // Mostrar la guía
            showGuide()
            // Marcar que ya no es la primera vez
            sharedPreferences.edit().putBoolean("isFirstTimeSettings", false).apply()
        }

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

    private fun setupLanguageSpinner() {
        // Configurar el adaptador del Spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languageNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        // Seleccionar el idioma previamente guardado
        val savedLanguage = sharedPreferences.getString("language", "en") // Default: English
        val selectedIndex = languageCodes.indexOf(savedLanguage)
        if (selectedIndex >= 0) {
            binding.spinner.setSelection(selectedIndex)
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = languageCodes[position]
                changeLanguage(selectedLanguage)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada
            }
        }
    }

    private fun changeLanguage(languageCode: String) {
        // Guardar el idioma seleccionado en SharedPreferences
        sharedPreferences.edit().putString("language", languageCode).apply()

        // Cambiar el idioma
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = requireContext().resources.configuration
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

        // Mostrar un mensaje
        showToast(getString(R.string.idioma_cambiado))
    }

    private fun showGuide() {
        // Mostrar el overlay y el mensaje de la guía
        binding.overlay.visibility = View.VISIBLE
        binding.guideText.text = "Esta es la pantalla de Ajustes"

        binding.finishGuideButton.setOnClickListener {
            // Ocultar el overlay cuando el usuario haga clic en el botón "Finalizar guía"
            binding.overlay.visibility = View.GONE
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
