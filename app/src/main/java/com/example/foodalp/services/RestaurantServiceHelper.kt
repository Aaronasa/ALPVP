package com.example.foodalp.services

import android.util.Log
import com.example.foodalp.models.RestaurantModel

class RestaurantServiceHelper(private val apiService: RestaurantAPIService) {

    suspend fun getRestaurantById(id: Int): RestaurantModel {
        Log.d("RestaurantService", "Fetching restaurant with ID: $id")
        val response = apiService.getRestaurantById(id)
        Log.d("RestaurantService", "Received Response: $response")
        return response
    }
}