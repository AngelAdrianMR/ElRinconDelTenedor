package com.example.elrincondeltenedor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.elrincondeltenedor.databinding.ScreenUserProfileBinding

class ProfileUser : AppCompatActivity() {

    private lateinit var binding: ScreenUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ScreenUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}