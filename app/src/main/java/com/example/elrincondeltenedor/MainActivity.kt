package com.example.elrincondeltenedor

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.elrincondeltenedor.databinding.HomeScreen01Binding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: HomeScreen01Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Usar View Binding para inflar el layout de home_screen_01
        binding = HomeScreen01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el click del botón para navegar a Home02
        binding.botonHome02.setOnClickListener {
            val intent = Intent(this, Home02::class.java)
            startActivity(intent)
        }
    }
}






