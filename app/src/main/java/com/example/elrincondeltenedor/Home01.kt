package com.example.elrincondeltenedor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Home01 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        binding = ValoracionesRecyclerviewBinding.inflate(layoutInflater)
        setContentView(R.layout.home_screen_01)

    }
}