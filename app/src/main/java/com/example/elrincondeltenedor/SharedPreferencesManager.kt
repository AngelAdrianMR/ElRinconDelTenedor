package com.example.elrincondeltenedor

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MisAjustes", Context.MODE_PRIVATE)

    // Métodos para guardar y recuperar datos
    fun saveUserName(nombre: String) {
        sharedPreferences.edit().putString("nombre_usuario", nombre).apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString("nombre_usuario", "Nombre Usuario")
    }

    fun saveUserPassword(correo: String) {
        sharedPreferences.edit().putString("correo_usuario", correo).apply()
    }

    fun getUserPassword(): String? {
        return sharedPreferences.getString("correo_usuario", "correousuario@gmail.com")
    }

}