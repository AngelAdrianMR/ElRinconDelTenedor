package com.example.elrincondeltenedor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var imageMenu: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.home_screen_01) // Asegúrate de inflar el layout correcto

        // Inicializar la Toolbar
        toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)

        // Inicializar el ImageView
        imageMenu = findViewById(R.id.imageMenu)
        imageMenu.setOnClickListener {
            Log.d("BaseActivity", "ImageMenu clicked")
            showPopupMenu(it)
        }
    }

    private fun showPopupMenu(view: View) {
        // Crea un PopupMenu
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_options, popupMenu.menu)

        // Manejar clics de elementos del menú
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_settings -> {
                    startActivity(Intent(this, SettingFragment::class.java))
                    true
                }
                R.id.action_collection -> {
                    startActivity(Intent(this, Collection::class.java))
                    true
                }
                R.id.action_profile -> {
                    startActivity(Intent(this, ProfileUserFragment::class.java))
                    true
                }
                R.id.action_logout -> {
                    // Manejar la acción de cerrar sesión
                    true
                }
                else -> false
            }
        }

        popupMenu.show() // Muestra el menú
    }

    // Inflar el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }
}