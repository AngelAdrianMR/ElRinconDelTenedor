package com.example.elrincondeltenedor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.elrincondeltenedor.databinding.CreateAccountBinding
import java.nio.file.LinkOption

class CreateAccountActivity: AppCompatActivity (R.layout.create_account) {
    private lateinit var binding: CreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = CreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button: Button = findViewById(R.id.botonCrearCuenta)
        button.setOnClickListener {

            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)

        }

        val linkTextView: TextView = findViewById(R.id.SingUp)

        linkTextView.setOnClickListener {
            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }
    }
}