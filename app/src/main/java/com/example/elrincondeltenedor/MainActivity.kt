package com.example.elrincondeltenedor

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.elrincondeltenedor.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationHelper: NotificationHelper
    private lateinit var auth: FirebaseAuth

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

        // Configurar el idioma inicial
        setInitialLanguage()

        // Inicializa NotificationHelper
        notificationHelper = NotificationHelper(this)
        notificationHelper.createNotificationChannel()

        checkNotificationPermission()
        initializeNotificacion()

        // Initialize Firebase Auth
        auth = Firebase.auth
        getCurrentUser()
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
                R.id.action_setting -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }
                R.id.action_logout -> {
                    logoutUser()
                    true
                }
                else -> false
            }
        }

        // Muestra el menú
        popupMenu.show()
    }

    private fun setInitialLanguage() {
        // Obtener las preferencias compartidas
        val sharedPreferences: SharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString("language", "en") ?: "en"

        // Configurar el idioma
        val locale = Locale(savedLanguage)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun initializeNotificacion() {
        // Crear y mostrar la notificación después de 5 segundos
        NotificationHelper(this).apply {
            createNotificationChannel()
            lifecycleScope.launch {
                delay(10000)
                showNewRestaurantNotification("Nuevo restaurante!", "A abierto un nuevo restaurante en tu zona.")
            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun logoutUser() {
        // Llama al método signOut de Firebase Auth
        Firebase.auth.signOut()

        // Redirige al usuario a la pantalla de inicio de sesión
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Cierra la actividad actual para evitar que el usuario vuelva con el botón atrás
    }

    private fun getCurrentUser() {
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid
        }
    }


}