package com.example.elrincondeltenedor

import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {
    // Lista que contiene los restaurantes
    private val restaurantList = mutableListOf<ItemData>()

    // Método para obtener un restaurante por su nombre
    fun getRestaurantByName(name: String): ItemData? {
        return restaurantList.find { it.name == name }
    }

    // Método para agregar un restaurante
    fun addRestaurant(restaurant: ItemData) {
        // Verifica si el restaurante ya existe
        if (getRestaurantByName(restaurant.name) == null) {
            restaurantList.add(restaurant)
        }
    }

    fun getAllRestaurants(): List<ItemData> {
        return restaurantList
    }

    // Método para obtener las valoraciones de un restaurante específico
    fun getValoracionesForRestaurant(name: String): List<ItemData_Valoraciones> {
        return getRestaurantByName(name)?.valoraciones ?: emptyList()
    }

    // Método para agregar una valoración a un restaurante específico
    fun addValoracionToRestaurant(name: String, valoracion: ItemData_Valoraciones) {
        // Encuentra el restaurante y agrega la valoración a la lista
        getRestaurantByName(name)?.let {
            it.valoraciones.add(valoracion)
        }
    }
}