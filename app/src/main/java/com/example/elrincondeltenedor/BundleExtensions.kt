package com.example.elrincondeltenedor

import android.os.Bundle

// Función para agregar GameData a un Bundle
fun Bundle.putRestaurantData(key: String, itemData: ItemData) {
    putString("$key.image", itemData.imageResId)
    putString("$key.name", itemData.name)
    putString("$key.description", itemData.text)
}

// Función para recuperar GameData de un Bundle
fun Bundle.getRestaurantData(key: String): ItemData {
    val image = getString("$key.image") ?: ""
    val name = getString("$key.name") ?: ""
    val description = getString("$key.description") ?: ""

    return ItemData(image, name, description)
}