package com.example.elrincondeltenedor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.Home02Binding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: Home02Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = Home02Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val items = listOf(
            CardView("1", R.drawable.logo)
        )

        binding.recyclerView.adapter = RecyclerViewAdapter(items)
    }
}