package com.example.elrincondeltenedor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.CollectionScreenBinding

class Collection : AppCompatActivity() {
    private lateinit var binding: CollectionScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = CollectionScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewCollection.layoutManager = LinearLayoutManager(this)
        val items = listOf(
            ItemData("1", R.drawable.logo)
        )

        binding.recyclerViewCollection.adapter = RecyclerViewAdapter_Collection(items)
    }
}