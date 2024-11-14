package com.example.elrincondeltenedor

import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {
    private val restaurantList = mutableListOf<ItemData>()

    fun getRestaurantByName(name: String): ItemData? {
        return restaurantList.find { it.name == name }
    }

    fun addRestaurant(restaurant: ItemData) {
        // Verifica si el restaurante ya existe en la lista antes de agregarlo
        if (getRestaurantByName(restaurant.name) == null) {
            restaurantList.add(restaurant)
        }
    }

    fun getAllRestaurants(): List<ItemData> {
        return restaurantList
    }

    // Método para agregar una valoración a un restaurante específico
    fun addValoracionToRestaurant(name: String, valoracion: ItemData_Valoraciones) {
        getRestaurantByName(name)?.let {
            it.valoraciones.add(valoracion)
        }
    }

    fun getValoracionesForRestaurant(name: String): List<ItemData_Valoraciones> {
        return getRestaurantByName(name)?.valoraciones ?: emptyList()
    }
}