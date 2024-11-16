package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.elrincondeltenedor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Configura la Toolbar como soporte para ActionBar
        val toolbar = findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolbar)

        // Vincula el NavController con la Toolbar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        // Configura la navegación con la Toolbar
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Configura el menú flotante
        configureFloatingMenu()
    }

    private fun configureFloatingMenu() {
        val fabMenu = findViewById<ImageView>(R.id.imageMenu)
        fabMenu.setOnClickListener { view ->
            showPopupMenu(view)
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_options, popupMenu.menu)

        // Obtiene el NavController desde el NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Configura el listener del menú
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.action_collection -> {
                    navController.navigate(R.id.collectionFragment)
                    true
                }
                R.id.action_profile -> {
                    navController.navigate(R.id.profileUserFragment)
                    true
                }
                R.id.action_home01 -> {
                    navController.navigate(R.id.home01Fragment)
                    true
                }
                R.id.action_logout -> {
                    navController.navigate(R.id.loginFragment)
                    true
                }
                else -> false
            }
        }

        // Muestra el menú
        popupMenu.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}