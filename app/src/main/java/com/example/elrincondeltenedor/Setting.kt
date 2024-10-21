package com.example.elrincondeltenedor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.elrincondeltenedor.databinding.SettingScreenBinding

class Setting : AppCompatActivity(){
    private lateinit var binding: SettingScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SettingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}