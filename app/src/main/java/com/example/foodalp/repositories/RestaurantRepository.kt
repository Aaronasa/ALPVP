package com.example.foodalp.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.foodalp.models.CreateRestaurantRequest
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.models.RestaurantResponse
import com.example.foodalp.models.UpdateRestaurantRequest
import com.example.foodalp.services.RestaurantAPIService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RestaurantRepository(private val apiService: RestaurantAPIService) {

    suspend fun addRestaurant(restaurant: RestaurantModel) {
        apiService.addRestaurant(restaurant)
    }

//    suspend fun getAllRestaurants(): List<RestaurantModel> {
//        return try {
//            val restaurants = apiService.getAllRestaurants()
//            Log.d("Repository", "Fetched Restaurants: $restaurants")
//            restaurants
//        } catch (e: Exception) {
//            Log.e("Repository", "Error fetching restaurants: ${e.message}", e)
//            throw e
//        }
//    }
    suspend fun getAllRestaurants(): RestaurantResponse {
        return apiService.getAllRestaurants()
    }

    suspend fun getRestaurantById(id: Int): RestaurantModel {
        val response = apiService.getRestaurantById(id)
        Log.d("Repository", "Received API Response: $response")
        return response
    }

suspend fun createRestaurant(request: CreateRestaurantRequest, context: Context): RestaurantModel {
    Log.d("Repository", "Preparing request for: $request")

    Log.d("Repository", "Raw Name: ${request.name}")
    Log.d("Repository", "Raw Address: ${request.address}")
    Log.d("Repository", "Raw Phone: ${request.phone}")
    Log.d("Repository", "Raw Image: ${request.image}")

    val namePart = RequestBody.create("text/plain".toMediaTypeOrNull(), request.name)
    val addressPart = RequestBody.create("text/plain".toMediaTypeOrNull(), request.address)
    val phonePart = RequestBody.create("text/plain".toMediaTypeOrNull(), request.phone)

    // Check if image is null and throw an exception if it is
    val imagePart = request.image
        ?: throw Exception("Image is required for creating a restaurant")

    Log.d("Repository", "Name Part: $namePart")
    Log.d("Repository", "Address Part: $addressPart")
    Log.d("Repository", "Phone Part: $phonePart")
    Log.d("Repository", "Image Part: ${imagePart.body.contentType()} - ${imagePart.body.contentLength()}")

    // Use the apiService to send the request
    return apiService.createRestaurant(namePart, addressPart, phonePart, imagePart)
}

    suspend fun updateRestaurant(request: UpdateRestaurantRequest): RestaurantModel =
        apiService.updateRestaurant(request)

    suspend fun deleteRestaurant(id: Int): RestaurantModel = apiService.deleteRestaurant(id)
}
