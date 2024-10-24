package com.example.elrincondeltenedor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.elrincondeltenedor.databinding.LoginScreenBinding

class loginActivity : AppCompatActivity(R.layout.login_screen) {

    private lateinit var binding: LoginScreenBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar SharedPreferencesManager correctamente
        sharedPreferencesManager = SharedPreferencesManager(this) // Usar 'this' como contexto

        // Cargar datos guardados al iniciar la actividad
        cargarDatosGuardados()

        val button: Button = findViewById(R.id.botonIniciar)
        button.setOnClickListener {
            guardarDatos() // Guardar los datos cuando se haga clic en el botón

            // Iniciar la MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
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