package com.example.elrincondeltenedor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.Home02CardviewBinding
import com.example.elrincondeltenedor.databinding.ScreenHome02Binding


class Home02 : AppCompatActivity() {
    private lateinit var binding: ScreenHome02Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ScreenHome02Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewHome02.layoutManager = LinearLayoutManager(this)
        val items = listOf(
            ItemData("1", R.drawable.logo)
        )

        binding.recyclerViewHome02.adapter = RecyclerViewAdapter_Home02(items)
    }
}