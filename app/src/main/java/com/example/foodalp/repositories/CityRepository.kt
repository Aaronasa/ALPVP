package com.example.foodalp.repositories

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.foodalp.AppContainer
import com.example.foodalp.models.CityModel
import com.example.foodalp.models.CreateCityRequest
import com.example.foodalp.services.CityAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class CityRepository(private val apiService: CityAPIService) {


    suspend fun getAllCity(token: String): List<CityModel> =
        withContext(Dispatchers.IO) {
            try {
                // Log request details (optional, useful for debugging)
                Log.d("Repository", "Fetching all restaurants...")

                // Make the API call
                val response = apiService.getAllCity(token)

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


    suspend fun getCityById(token: String, id: Int): CityModel? =
        withContext(Dispatchers.IO) {
            try {
                val response = AppContainer.cityService.getCityById(token, id)
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("CityService", "Raw Response Body: $apiResponse")
                    apiResponse?.data
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e("CityService", "Error Response: $errorMessage")
                    throw Exception("API Error: $errorMessage")
                }
            } catch (e: Exception) {
                Log.e("CityService", "Error fetching City: ${e.message}", e)
                throw e
            }
        }

    suspend fun deleteCity(token: String, id: Int): CityModel? {
        return withContext(Dispatchers.IO) {
            try {
                apiService.deleteCity(token, id)
            } catch (e: Exception) {
                Log.e("CityRepository", "Error deleting city: ${e.message}", e)
                null
            }
        }
    }


    suspend fun createCity(
        token: String,
        request: CreateCityRequest,
        context: Context
    ): CityModel {
        Log.d("Repository", "Preparing request for: $request")

        Log.d("Repository", "Raw Name: ${request.name}")
        Log.d("Repository", "Raw Image: ${request.image}")

        val namePart = RequestBody.create("text/plain".toMediaTypeOrNull(), request.name)

        // Check if image is null and throw an exception if it is
        val imagePart = request.image
            ?: throw Exception("Image is required for creating a restaurant")

        Log.d("Repository", "Name Part: $namePart")
        Log.d(
            "Repository",
            "Image Part: ${imagePart.body.contentType()} - ${imagePart.body.contentLength()}"
        )

        // Use the apiService to send the request
        return apiService.createCity(token, namePart, imagePart)
    }

    suspend fun updateCity(
        token: String,
        id: Int,
        name: String,
        imageUri: Uri?,
        context: Context
    ): CityModel {
        return withContext(Dispatchers.IO) {
            try {
                // Prepare name part
                val namePart = name.toRequestBody("text/plain".toMediaTypeOrNull())

                // Prepare image part if imageUri is not null
                val imagePart = imageUri?.let {
                    val file = File(getRealPathFromURI(context, it))
                    val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("image", file.name, requestBody)
                }

                // Call API to update city
                val response = apiService.updateCity(token, id, imagePart, namePart)
                Log.d("CityRepository", "City updated successfully: $response")
                response
            } catch (e: Exception) {
                Log.e("CityRepository", "Error updating city: ${e.message}", e)
                throw e
            }
        }
    }


    private fun getRealPathFromURI(context: Context, uri: Uri): String {
        var filePath: String? = null
        if (uri.scheme == "content") {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver.query(uri, projection, null, null, null)
            cursor?.let {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                it.moveToFirst()
                filePath = it.getString(columnIndex)
                it.close()
            }
        } else if (uri.scheme == "file") {
            filePath = uri.path
        }
        return filePath ?: throw Exception("Failed to resolve file path")
    }


}
