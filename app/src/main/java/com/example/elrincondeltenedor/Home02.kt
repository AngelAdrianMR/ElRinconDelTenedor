package com.example.elrincondeltenedor

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elrincondeltenedor.databinding.CollectionScreenBinding
import com.example.elrincondeltenedor.databinding.ScreenHome02Binding

class Home02 : BaseActivity() {
    private lateinit var binding: ScreenHome02Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ScreenHome02Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewHome02.layoutManager = LinearLayoutManager(this)
        val items = listOf(
            ItemData("1", R.drawable.casa)
        )

        binding.recyclerViewHome02.adapter = RecyclerViewAdapter_Collection(items)

        // Configurar el click del botón para navegar a Home01
        binding.botonHome01.setOnClickListener {
            val intent = Intent(this, Home01::class.java)
            startActivity(intent)
        }
    }
}