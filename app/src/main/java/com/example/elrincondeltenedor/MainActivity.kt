package com.example.elrincondeltenedor

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
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
    private var guideStep = 0
    private val selectedMenuItems = mutableSetOf<Int>() // Para rastrear las opciones del menú seleccionadas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


            // Sonido de fondo
        val mediaPlayer = MediaPlayer.create(this, R.raw.retro11s)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
        }


        // Configurar la Toolbar como soporte para ActionBar
        val toolbar = findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolbar)

        // Configurar la navegación
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Configurar el idioma inicial
        setInitialLanguage()

        // Configurar notificaciones
        notificationHelper = NotificationHelper(this)
        notificationHelper.createNotificationChannel()
        checkNotificationPermission()
        initializeNotification()

        // Inicializar Firebase Auth
        auth = Firebase.auth

        // Configurar menú flotante
        configureFloatingMenu()

        // Configurar la guía si es la primera vez que se abre la app
        configureGuide()
    }

    private fun configureGuide() {
        val sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

        // Mostrar la guía si es la primera vez que se abre la app
        if (sharedPreferences.getBoolean("is_first_launch", true)) {
            showGuide()
            sharedPreferences.edit().putBoolean("is_first_launch", false).apply() // Marcar que ya se ha lanzado la app
        }
    }

    private fun showGuide() {
        binding.overlay.visibility = View.VISIBLE
        binding.guideText.visibility = View.VISIBLE
        binding.startGuideButton.visibility = View.VISIBLE
        binding.skipGuideButton.visibility = View.VISIBLE


        binding.startGuideButton.setOnClickListener {
            binding.startGuideButton.visibility = View.GONE
            binding.skipGuideButton.visibility = View.VISIBLE
            binding.nextButton.visibility = View.VISIBLE
            updateGuideStep() // Actualiza el paso de la guía
        }

        binding.skipGuideButton.setOnClickListener {
            endGuide() // Cierra la guía
        }

        binding.nextButton.setOnClickListener {
            handleGuideStep() // Maneja los pasos siguientes
        }
    }

    private fun updateGuideStep() {
        when (guideStep) {
            0 -> {
                binding.guideText.text = "Este es el botón de la lista de restaurantes."
                highlightButton(findViewById(R.id.botonHome02))
            }
            1 -> {
                binding.guideText.text = "Este es el botón del menú principal."
                highlightButton(findViewById(R.id.imageMenu))
            }
            2 -> {
                binding.guideText.text = "Ahora puedes usar el menú para explorar más opciones."
                unlockMenuButton()  // Desbloquear el botón del menú
            }
            3 -> {
                binding.guideText.text = "Estas son las opciones del menú."
            }
            else -> {
                endGuide() // Fin de la guía
            }
        }
    }

    private fun handleGuideStep() {
        guideStep++
        updateGuideStep()
    }

    private fun unlockMenuButton() {
        // Desbloqueamos el botón para que el usuario pueda interactuar con el menú
        val fabMenu = findViewById<ImageView>(R.id.imageMenu)
        fabMenu.isEnabled = true
    }

    private fun endGuide() {
        binding.overlay.visibility = View.GONE
        binding.guideText.visibility = View.GONE
        binding.startGuideButton.visibility = View.GONE
        binding.skipGuideButton.visibility = View.GONE
        binding.nextButton.visibility = View.GONE
    }

    private fun highlightButton(button: View) {
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.highlight_color))
        button.postDelayed({
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.default_color))
        }, 1500) // Resalta el botón por 1.5 segundos
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

        // Configurar listener del menú
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            val sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
            val isFirstTime = sharedPreferences.getBoolean(menuItem.itemId.toString(), true)

            // Registrar que ya se ha accedido a esta opción del menú
            if (isFirstTime) {
                sharedPreferences.edit().putBoolean(menuItem.itemId.toString(), false).apply()
            }

            // Navegar a la pantalla correspondiente
            navigateToScreen(menuItem, navController)
            true
        }
        popupMenu.show()
    }

    private fun navigateToScreen(menuItem: MenuItem, navController: NavController) {
        when (menuItem.itemId) {
            R.id.action_collection -> {
                navController.navigate(R.id.collectionFragment)
            }
            R.id.action_profile -> {
                navController.navigate(R.id.action_home01Fragment_to_profileUserFragment)
            }
            R.id.action_home01 -> {
                navController.navigate(R.id.home01Fragment)
            }
            R.id.action_setting -> {
                navController.navigate(R.id.settingsFragment)
            }
            R.id.action_logout -> {
                logoutUser()
            }
        }
    }

    private fun setInitialLanguage() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString("language", "en") ?: "en"
        val locale = Locale(savedLanguage)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun initializeNotification() {
        lifecycleScope.launch {
            delay(10000)
            notificationHelper.showNewRestaurantNotification("Nuevo restaurante!", "Ha abierto un nuevo restaurante en tu zona.")
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }
    }

    private fun logoutUser() {
        Firebase.auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
