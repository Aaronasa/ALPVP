package com.example.foodalp.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.foodalp.AppContainer
import com.example.foodalp.models.CreateRestaurantRequest
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.models.RestaurantResponse
import com.example.foodalp.models.ReviewModel
import com.example.foodalp.models.UpdateRestaurantRequest
import com.example.foodalp.services.RestaurantAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

class RestaurantRepository(private val apiService: RestaurantAPIService) {

    suspend fun getAllRestaurants(token: String): List<RestaurantModel> =
        withContext(Dispatchers.IO) {
            try {
                Log.d("Repository", "Fetching all restaurants...")
                val response = apiService.getAllRestaurants(token)
                Log.d("Repository", "Successfully fetched restaurants: ${response.data}")
                response.data
            } catch (e: HttpException) {
                    if (e.code() == 403) {
                    Log.e("Repository", "403 Forbidden: ${e.response()?.errorBody()?.string()}")
                    throw Exception("Access denied: Please check your authentication or permissions.")
                } else {
                    Log.e("Repository", "HTTP error: ${e.code()} - ${e.message()}")
                    throw e
                }
            } catch (e: Exception) {
                Log.e("Repository", "Error fetching restaurants: ${e.message}", e)
                throw e
            }
        }

    suspend fun getAllRestaurantsAdmin(token: String): List<RestaurantModel> =
        withContext(Dispatchers.IO) {
            try {
                Log.d("Repository", "Fetching all restaurants...")
                val response = apiService.getAllRestaurantsAdmin(token)
                Log.d("Repository", "Successfully fetched restaurants: ${response.data}")
                response.data
            } catch (e: HttpException) {
                if (e.code() == 403) {
                    Log.e("Repository", "403 Forbidden: ${e.response()?.errorBody()?.string()}")
                    throw Exception("Access denied: Please check your authentication or permissions.")
                } else {
                    Log.e("Repository", "HTTP error: ${e.code()} - ${e.message()}")
                    throw e
                }
            } catch (e: Exception) {
                Log.e("Repository", "Error fetching restaurants: ${e.message}", e)
                throw e
            }
        }


    suspend fun getRestaurantById(token: String, id: Int): RestaurantModel? =
        withContext(Dispatchers.IO) {
        try {
            val response = AppContainer.restaurantService.getRestaurantById(token, id)
            if (response.isSuccessful) {
                val apiResponse = response.body()
                Log.d("RestaurantService", "Raw Response Body: $apiResponse")
                apiResponse?.data
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
    suspend fun getRestaurantByIdAdmin(token: String, id: Int): RestaurantModel? =
        withContext(Dispatchers.IO) {
            try {
                val response = AppContainer.restaurantService.getRestaurantByIdAdmin(token, id)
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("RestaurantService", "Raw Response Body: $apiResponse")
                    apiResponse?.data
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

        return apiService.createRestaurant(token, namePart, addressPart, phonePart, imagePart)
    }

    suspend fun createRestaurantAdmin(
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

        return apiService.createRestaurantAdmin(token, namePart, addressPart, phonePart, imagePart)
    }

    suspend fun updateRestaurant(token: String, id: Int, request: UpdateRestaurantRequest): RestaurantModel {
        // Convert strings to RequestBody
        val nameBody = request.name?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val addressBody = request.address?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val phoneBody = request.phone?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }

        return apiService.updateRestaurant(
            token = token,
            id = id,
            name = nameBody,
            address = addressBody,
            phone = phoneBody,
            image = request.image
        )
    }

    suspend fun deleteRestaurant(id: Int, token: String): RestaurantModel {
        Log.d("Restaurant Repository", "Calling API to delete restaurant ID: $id")
        val result = apiService.deleteRestaurant(token, id)
        Log.d("Restaurant Repository", "API call completed for delete restaurant ID: $id")
        return result
    }
}
