package com.example.elrincondeltenedor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.elrincondeltenedor.databinding.ActivityMainBinding
import com.example.elrincondeltenedor.databinding.ValoracionesRecyclerviewBinding

class MainActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding
    private lateinit var binding: ValoracionesRecyclerviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        binding = ActivityMainBinding.inflate(layoutInflater)
        binding = ValoracionesRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}