package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.elrincondeltenedor.databinding.ActivityMainBinding
import com.example.elrincondeltenedor.databinding.ToolbarGeneralBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbarBinding: ToolbarGeneralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inflar el binding para la Toolbar
        toolbarBinding = ToolbarGeneralBinding.inflate(layoutInflater)
        setSupportActionBar(toolbarBinding.toolBar) // Configura la Toolbar como ActionBar

        // Configura el NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Configura la AppBar
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        // Configurar la Toolbar con el NavController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(toolbarBinding.toolBar, navController)
    }

    // Inflar el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu) // Asegúrate de que el archivo de menú existe
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        return when (item.itemId) {
            R.id.action_settings -> {
                navController.navigate(R.id.settingsFragment)
                true
            }
            R.id.action_collection -> {
                navController.navigate(R.id.collectionFragment)
                true
            }
            R.id.action_profile -> {
                navController.navigate(R.id.profileUserFragment)
                true
            }
            android.R.id.home -> {
                navController.navigate(R.id.home01Fragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}