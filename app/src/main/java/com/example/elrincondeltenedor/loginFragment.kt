package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.elrincondeltenedor.databinding.LoginScreenBinding

class LoginFragment : Fragment() {

    private lateinit var _binding: LoginScreenBinding
    private val binding get() = _binding
    private lateinit var sharedPreferencesManager: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginScreenBinding.inflate(inflater, container, false)

        // Inicializar SharedPreferencesManager
        sharedPreferencesManager = SharedPreferences(requireActivity())

        // Cargar datos desde SharedPreferences y mostrarlos en la UI
        cargarDatosGuardados()

        // Guardar los datos cuando el usuario haga clic en el botón
        binding.button.setOnClickListener {
            guardarDatos()
        }

        return binding.root
    }

    private fun cargarDatosGuardados() {
        // Cargar nombre y correo guardados en SharedPreferences
        val nombreUsuario = sharedPreferencesManager.getUserName()
        val correoUsuario = sharedPreferencesManager.getUserPassword()

        // Asignar los valores a los EditTexts correspondientes
        binding.email.setText(nombreUsuario ?: "") // Usar el operador Elvis para manejar null
        binding.psw.setText(correoUsuario ?: "")   // Usar el operador Elvis para manejar null
    }

    private fun guardarDatos() {
        // Guardar los nuevos valores en SharedPreferencesManager
        sharedPreferencesManager.saveUserName(binding.email.text.toString())
        sharedPreferencesManager.saveUserPassword(binding.psw.text.toString())
    }
}