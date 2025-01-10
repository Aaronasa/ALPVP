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
import com.example.foodalp.ui.state.RestaurantState
import com.example.foodalp.ui.state.RestaurantUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class RestaurantViewModel : ViewModel() {

    private val repository = RestaurantRepository(AppContainer.restaurantService)

    // UI State for Restaurant operations
    private val _uiState = MutableStateFlow(RestaurantUIState())
    val uiState: StateFlow<RestaurantUIState> get() = _uiState.asStateFlow()

    private val _restaurants = MutableStateFlow<List<RestaurantModel>>(emptyList())
    val restaurants: StateFlow<List<RestaurantModel>> get() = _restaurants.asStateFlow()

    private val _selectedRestaurant = MutableStateFlow<RestaurantModel?>(null)
    val selectedRestaurant: StateFlow<RestaurantModel?> get() = _selectedRestaurant.asStateFlow()

    private val _operationState = MutableStateFlow<RestaurantState>(RestaurantState.Start)
    val operationState: StateFlow<RestaurantState> get() = _operationState.asStateFlow()

    // Fetch all restaurants
    fun fetchAllRestaurants(token: String) {
        _operationState.value = RestaurantState.Loading
        viewModelScope.launch {
            try {
                val restaurants = repository.getAllRestaurants(token)
                _restaurants.value = restaurants
                _operationState.value = RestaurantState.Success(restaurants)
                Log.d("RestaurantViewModel", "Fetched restaurants: $restaurants")
            } catch (e: Exception) {
                _operationState.value = RestaurantState.Failed(e.message ?: "Unknown error")
                Log.e("RestaurantViewModel", "Error fetching restaurants: ${e.message}", e)
            }
        }
    }

    // Fetch restaurant by ID
    suspend fun fetchRestaurantById(token: String, restaurantId: Int): RestaurantModel? {
        return try {
            val restaurant = repository.getRestaurantById(token, restaurantId)
            _selectedRestaurant.value = restaurant // Jika diperlukan untuk state management
            _operationState.value = RestaurantState.Success(listOf(restaurant))
            Log.d("RestaurantViewModel", "Fetched restaurant by ID: $restaurant")
            restaurant // Kembalikan hasil
        } catch (e: Exception) {
            _operationState.value = RestaurantState.Failed(e.message ?: "Unknown error")
            Log.e("RestaurantViewModel", "Error fetching restaurant by ID: ${e.message}", e)
            null // Jika terjadi error, kembalikan null
        }
    }

    // Create a new restaurant
    fun createRestaurant(token: String, context: Context, request: CreateRestaurantRequest) {
        _operationState.value = RestaurantState.Loading
        viewModelScope.launch {
            try {
                Log.d("RestaurantViewModel", "Sending createRestaurant request: $request")
                val imagePart = request.image ?: throw Exception("Image is required for creating a restaurant")
                repository.createRestaurant(token, request.copy(image = imagePart), context)
                fetchAllRestaurants(token) // Refresh after creation
                _operationState.value = RestaurantState.Success(_restaurants.value)
                Log.d("RestaurantViewModel", "Restaurant created successfully")
            } catch (e: Exception) {
                _operationState.value = RestaurantState.Failed(e.message ?: "Error creating restaurant")
                Log.e("RestaurantViewModel", "Error creating restaurant: ${e.message}", e)
            }
        }
    }

    // Update an existing restaurant
    fun updateRestaurant(request: UpdateRestaurantRequest, token: String) {
        _operationState.value = RestaurantState.Loading
        viewModelScope.launch {
            try {
                repository.updateRestaurant(request)
                fetchAllRestaurants(token) // Refresh after update
                _operationState.value = RestaurantState.Success(_restaurants.value)
                Log.d("RestaurantViewModel", "Restaurant updated successfully")
            } catch (e: Exception) {
                _operationState.value = RestaurantState.Failed(e.message ?: "Error updating restaurant")
                Log.e("RestaurantViewModel", "Error updating restaurant: ${e.message}", e)
            }
        }
    }

    // Delete a restaurant
    fun deleteRestaurant(id: Int, token: String) {
        _operationState.value = RestaurantState.Loading
        viewModelScope.launch {
            try {
                repository.deleteRestaurant(id)
                fetchAllRestaurants(token) // Refresh after deletion
                _operationState.value = RestaurantState.Success(_restaurants.value)
                Log.d("RestaurantViewModel", "Restaurant deleted successfully")
            } catch (e: Exception) {
                _operationState.value = RestaurantState.Failed(e.message ?: "Error deleting restaurant")
                Log.e("RestaurantViewModel", "Error deleting restaurant: ${e.message}", e)
            }
        }
    }

    // Helper: Create MultipartBody for images
    fun createImagePart(context: Context, imageUri: Uri): MultipartBody.Part {
        val file = File(getRealPathFromURI(context, imageUri))
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestBody)
    }

    // Helper: Convert URI to file path
    private fun getRealPathFromURI(context: Context, uri: Uri): String? {
        var filePath: String? = null
        if (uri.scheme == "content") {
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
}