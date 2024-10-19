package com.example.elrincondeltenedor

import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toolbar
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

    protected fun setupToolbarWithMenu() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val menuImageView: ImageView = findViewById(R.id.fotoPerfil)
        menuImageView.setOnClickListener {
            showPopupMenu(menuImageView)
        }
    }

    private fun showPopupMenu(view: ImageView) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->


        popupMenu.show()
    }
}