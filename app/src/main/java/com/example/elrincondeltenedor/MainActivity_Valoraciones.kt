package com.example.elrincondeltenedor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.ActivityMainOpinionsBinding

class MainActivity_Valoraciones : AppCompatActivity() {

    private lateinit var binding: ActivityMainOpinionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainOpinionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewRestaurant.layoutManager = LinearLayoutManager(this)
        val items = listOf(
            ItemData_Valoraciones("Usuario 1", R.drawable.logo)
        )

        binding.recyclerViewRestaurant.adapter = RecyclerViewAdapter_Valoraciones(items)
    }
}