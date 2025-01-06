package com.example.foodalp.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodalp.AppContainer
import com.example.foodalp.models.CreateRestaurantRequest
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.models.UpdateRestaurantRequest
import com.example.foodalp.repositories.RestaurantRepository
import com.example.foodalp.ui.state.RestaurantUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class RestaurantViewModel() : ViewModel() {

    private val repository = RestaurantRepository(AppContainer.restaurantService)
    private val _uiState = MutableStateFlow(RestaurantUIState())
    val uiState: StateFlow<RestaurantUIState> get() = _uiState


    fun fetchAllRestaurants() {
        viewModelScope.launch {
            try {
                Log.d("ViewModel", "Fetching all restaurants...")
                _uiState.value = _uiState.value.copy(isLoading = true)
                val response = repository.getAllRestaurants()

                // Access the list of restaurants
                val restaurants = response.data
                restaurants.forEach { restaurant ->
                    Log.d("ViewModel", "Restaurant Image URL: ${restaurant.image}")
                }


                Log.d("ViewModel", "Fetched restaurants: $restaurants")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    restaurants = restaurants
                )
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching all restaurants: ${e.message}", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun fetchRestaurantById(restaurantId: Int, callback: (RestaurantModel?) -> Unit) {
        Log.d("ViewModel", "Fetching restaurant by id: $restaurantId")
        viewModelScope.launch {
            try {
                // Test API response directly here
                val response = repository.getRestaurantById(restaurantId)
                Log.d("viemodel stlh masuk ke repository", "Response setelah masuk ke repository: $response")

                callback(response) // Pass the response to the callback
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching restaurant by id: ${e.message}", e)
                callback(null)
            }
        }
    }

    fun createRestaurant(context: Context, request: CreateRestaurantRequest) {
        viewModelScope.launch {
            try {
                Log.d("ViewModel", "Sending createRestaurant request: $request")

                // Ensure the image is not null
                val imagePart = request.image
                    ?: throw Exception("Image is required for creating a restaurant") // Throw an exception if the image is null

                // Create the restaurant with the image part
                repository.createRestaurant(request.copy(image = imagePart), context)

                Log.d("ViewModel", "Restaurant created successfully")
                fetchAllRestaurants() // Refresh after creation
                _uiState.value = _uiState.value.copy(errorMessage = null)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error creating restaurant: ${e.message}")
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
                Log.e("RestaurantViewModel", "Error creating restaurant: ${e.message}")
            }
        }
    }

    fun createImagePart(context: Context, imageUri: Uri): MultipartBody.Part {
        val file = File(getRealPathFromURI(context, imageUri)) // Convert URI to actual file
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull()) // Adjust content type accordingly
        return MultipartBody.Part.createFormData("image", file.name, requestBody)
    }

    fun getRealPathFromURI(context: Context, uri: Uri): String? {
        var filePath: String? = null

        // Check if the URI is a content URI
        if (uri.scheme == "content") {
            // Query the content resolver for the file path
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver.query(uri, proj, null, null, null)

            if (cursor != null) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                filePath = cursor.getString(columnIndex)
                cursor.close()
            }
        } else if (uri.scheme == "file") {
            filePath = uri.path
        }

        return filePath
    }

    fun updateRestaurant(request: UpdateRestaurantRequest) {
        viewModelScope.launch {
            try {
                repository.updateRestaurant(request)
                fetchAllRestaurants() // Refresh after update
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun deleteRestaurant(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteRestaurant(id)
                fetchAllRestaurants() // Refresh after deletion
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

//    fun saveRestaurantID(context: Context, restaurantId: Int) {
//        val RestauantsharedPreferences = context.getSharedPreferences("restaurant_id", Context.MODE_PRIVATE)
//        val Restauranteditor = RestauantsharedPreferences.edit()
//        Restauranteditor.putInt("restaurant_id", restaurantId)
//        Restauranteditor.apply()
//    }
}
