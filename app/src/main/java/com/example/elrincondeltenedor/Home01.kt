package com.example.elrincondeltenedor

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.elrincondeltenedor.databinding.HomeScreen01Binding

class Home01 : BaseActivity() {
    private lateinit var binding: HomeScreen01Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = HomeScreen01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el click del botón para navegar a Home02
        binding.botonHome02.setOnClickListener {
            val intent = Intent(this, Home02::class.java)
            startActivity(intent)
        }
    }
}