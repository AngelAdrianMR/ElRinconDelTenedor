package com.example.elrincondeltenedor

import androidx.appcompat.widget.Toolbar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding
//    private lateinit var binding: ValoracionesRecyclerviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        binding = ValoracionesRecyclerviewBinding.inflate(layoutInflater)
        setContentView(R.layout.home_screen_01)
        setupToolbarWithMenu()

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
            when (item.itemId) {
                R.id.settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, OptionsFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.user -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    class OptionsFragment : Fragment(R.layout.setting_screen) {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.setting_screen, container, false)
        }
    }

    class ProfileFragment : Fragment(R.layout.screen_user_profile) {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.screen_user_profile, container, false)
        }
    }
}
