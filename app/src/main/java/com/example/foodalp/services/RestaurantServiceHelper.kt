package com.example.foodalp.services

import android.util.Log
import com.example.foodalp.models.RestaurantModel
import retrofit2.Response

class RestaurantServiceHelper(private val apiService: RestaurantAPIService) {

    suspend fun getRestaurantById(id: Int) {
        Log.d("RestaurantService", "Fetching restaurant with ID: $id")
        val response = apiService.getRestaurantById(id.toString())
        Log.d("RestaurantService", "Received Response: $response")
        return response
    }
}