package com.example.foodalp.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.foodalp.AppContainer
import com.example.foodalp.models.CreateRestaurantRequest
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.models.RestaurantResponse
import com.example.foodalp.models.UpdateRestaurantRequest
import com.example.foodalp.services.RestaurantAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File

class RestaurantRepository(private val apiService: RestaurantAPIService) {


    suspend fun getAllRestaurants(token: String): List<RestaurantModel> =
        withContext(Dispatchers.IO) {
            try {
                // Log request details (optional, useful for debugging)
                Log.d("Repository", "Fetching all restaurants...")

                // Make the API call
                val response = apiService.getAllRestaurants(token)

                // Log success response (optional)
                Log.d("Repository", "Successfully fetched restaurants: ${response.data}")

                response.data
            } catch (e: HttpException) {
                // Check for specific HTTP status codes
                if (e.code() == 403) {
                    Log.e("Repository", "403 Forbidden: ${e.response()?.errorBody()?.string()}")
                    throw Exception("Access denied: Please check your authentication or permissions.")
                } else {
                    Log.e("Repository", "HTTP error: ${e.code()} - ${e.message()}")
                    throw e
                }
            } catch (e: Exception) {
                // Handle general exceptions
                Log.e("Repository", "Error fetching restaurants: ${e.message}", e)
                throw e
            }
        }


    suspend fun getRestaurantById(token: String, id: Int): RestaurantModel =
        withContext(Dispatchers.IO) {
        try {
//            Log.d("RestaurantRepository", "Fetching restaurant with ID sebelum ke service: $id and token: $token")
//            // Pass the token and id correctly
//            val response = apiService.getRestaurantById(token, id)
//            // Log the response data for debugging
//            Log.d("RestaurantRepository", "Received Response setelah dari service: $response")
//            response
            val response = AppContainer.restaurantService.getRestaurantById(token, id)

            if (response.isSuccessful) {
                val restaurant = response.body()
                Log.d("RestaurantService", "Raw Response Body: $restaurant")
                return@withContext restaurant!!
            } else {
                val errorMessage = response.errorBody()?.string()
                Log.e("RestaurantService", "Error Response: $errorMessage")
                throw Exception("API Error: $errorMessage")
            }
        } catch (e: Exception) {
            Log.e("RestaurantRepository", "Error fetching restaurant: ${e.message}", e)
            throw e
        }
    }


    suspend fun createRestaurant(
        token: String,
        request: CreateRestaurantRequest,
        context: Context
    ): RestaurantModel {
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
        Log.d(
            "Repository",
            "Image Part: ${imagePart.body.contentType()} - ${imagePart.body.contentLength()}"
        )

        // Use the apiService to send the request
        return apiService.createRestaurant(token, namePart, addressPart, phonePart, imagePart)
    }

    suspend fun updateRestaurant(request: UpdateRestaurantRequest): RestaurantModel =
        apiService.updateRestaurant(request)

    suspend fun deleteRestaurant(id: Int): RestaurantModel = apiService.deleteRestaurant(id)
}
