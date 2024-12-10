package com.example.elrincondeltenedor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.elrincondeltenedor.databinding.CreateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateAccountActivity : AppCompatActivity(R.layout.create_account) {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: CreateAccountBinding

    companion object {
        private const val TAG = "CreateAccountActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = CreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Firebase Auth
        auth = Firebase.auth

        // Configurar listener para el botón de creación de cuenta
        binding.botonCrearCuenta.setOnClickListener {
            val email = binding.IntroduccionEmail.text.toString()
            val password = binding.IntroduccionPassword.text.toString()

            // Validar campos
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(
                    this,
                    "Por favor, completa todos los campos.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Crear usuario con Firebase Auth
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            this,
                            "Cuenta creada exitosamente.",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Redirigir al login
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this,
                            "Fallo en la creación de la cuenta: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        // Configurar listener para el enlace de inicio de sesión
        binding.SingUp.setOnClickListener { // Asegúrate de que el ID sea correcto en el XML
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d(TAG, "Usuario ya autenticado.")
        }
    }
}
