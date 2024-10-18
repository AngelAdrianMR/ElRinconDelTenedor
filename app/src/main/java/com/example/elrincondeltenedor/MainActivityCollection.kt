package com.example.elrincondeltenedor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.CollectionScreenBinding


class MainActivityCollection : AppCompatActivity() {

    private lateinit var binding: CollectionScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding= CollectionScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewRestaurant.layoutManager = LinearLayoutManager(this)
        val items = listOf(
            ItemData_Collection("1",R.drawable.logo)
        )

        binding.recyclerViewRestaurant.adapter = RecyclerViewAdapter_Collection(items)
    }
}