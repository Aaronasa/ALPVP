package com.example.foodalp.repositories

import android.util.Log
import com.example.foodalp.models.FoodModel
import com.example.foodalp.services.FoodAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class FoodRepository(private val foodAPIService: FoodAPIService) {

    companion object {
        private const val TAG = "FoodRepository"

        // Helper function to create RequestBody
        private fun String?.toRequestBodyOrNull(): RequestBody? =
            this?.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    suspend fun createFood(
        token: String,
        image: MultipartBody.Part,
        name: String,
        description: String,
        price: String
    ): FoodModel {
        try {
            Log.d(TAG, "Creating food: $name")
            foodAPIService.createFood(
                token = token,
                image = image,
                name = name.toRequestBodyOrNull()!!,
                description = description.toRequestBodyOrNull()!!,
                price = price.toRequestBodyOrNull()!!
            )
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error while creating food: ${e.code()} - ${e.message()}")
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error while creating food: ${e.message}", e)
            throw e
        }
        return TODO("Provide the return value")
    }

    suspend fun getFoodDetail(token: String, id: Int): FoodModel = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Fetching food detail for ID: $id")
            val response = foodAPIService.getFoodDetail(token, id) // Retrofit call

            if (response.isSuccessful) {
                response.body()?.data ?: throw Exception("Food detail not found in the response")
            } else {
                Log.e(TAG, "Failed to fetch food detail: HTTP ${response.code()} - ${response.message()}")
                throw HttpException(response)
            }
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error while fetching food detail: ${e.code()} - ${e.message()}")
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error while fetching food detail: ${e.message}", e)
            throw e
        }
    }

    suspend fun getAllFoods(token: String): List<FoodModel> = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Fetching all foods")
            val response = foodAPIService.getAllFoods(token)
            Log.d(TAG, "Successfully fetched ${response.data.size} foods")
            response.data
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error while fetching all foods: ${e.code()} - ${e.message()}")
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error while fetching all foods: ${e.message}", e)
            throw e
        }
    }

    suspend fun updateFood(
        token: String,
        id: Int,
        image: MultipartBody.Part?,
        name: String?,
        description: String?,
        price: String?
    ): FoodModel = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Updating food with ID: $id")
            foodAPIService.updateFood(
                token = token,
                id = id,
                image = image,
                name = name.toRequestBodyOrNull(),
                description = description.toRequestBodyOrNull(),
                price = price.toRequestBodyOrNull()
            )
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error while updating food: ${e.code()} - ${e.message()}")
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error while updating food: ${e.message}", e)
            throw e
        }
    }

    suspend fun deleteFood(token: String, id: Int): FoodModel = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Deleting food with ID: $id")
            foodAPIService.deleteFood(token, id)
        } catch (e: HttpException) {
            Log.e(TAG, "HTTP error while deleting food: ${e.code()} - ${e.message()}")
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error while deleting food: ${e.message}", e)
            throw e
        }
    }
}