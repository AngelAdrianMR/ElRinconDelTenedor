package com.example.elrincondeltenedor

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.elrincondeltenedor.databinding.LoginScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(R.layout.login_screen) {

    private lateinit var binding: LoginScreenBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        // Verificar si el usuario ya está autenticado
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        sharedPreferencesManager = SharedPreferencesManager(this)
        cargarDatosGuardados()

        binding.botonIniciar.setOnClickListener {
            iniciarSesion()
        }
    }

    private fun iniciarSesion() {
        val email = binding.email.text.toString()
        val psw = binding.psw.text.toString()

        if (email.isBlank() || psw.isBlank()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Por favor, ingresa un correo válido.", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, psw)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    guardarDatos()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    manejarErrorAutenticacion(task.exception)
                }
            }
    }

    private fun manejarErrorAutenticacion(exception: Exception?) {
        if (exception != null) {
            when (exception) {
                is FirebaseAuthInvalidCredentialsException -> {
                    Toast.makeText(this, "Credenciales inválidas.", Toast.LENGTH_SHORT).show()
                }
                is FirebaseAuthInvalidUserException -> {
                    Toast.makeText(this, "Usuario no encontrado.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Error de autenticación: ${exception.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun cargarDatosGuardados() {
        try {
            val nombreUsuario = sharedPreferencesManager.getUserName()
            val correoUsuario = sharedPreferencesManager.getUserPassword()
            binding.email.setText(nombreUsuario ?: "")
            binding.psw.setText(correoUsuario ?: "")
        } catch (e: Exception) {
            Log.e(TAG, "Error al cargar datos guardados", e)
        }
    }

    private fun guardarDatos() {
        try {
            sharedPreferencesManager.saveUserName(binding.email.text.toString())
            sharedPreferencesManager.saveUserPassword(binding.psw.text.toString())
        } catch (e: Exception) {
            Log.e(TAG, "Error al guardar datos", e)
        }
    }
}
