package com.example.elrincondeltenedor

import okhttp3.Response
import retrofit2.http.GET
import com.example.elrincondeltenedor.ItemResponse

interface ApiService {
    @GET("restaurant")
    suspend fun getRestaurant(): retrofit2.Response<ItemResponse>
}