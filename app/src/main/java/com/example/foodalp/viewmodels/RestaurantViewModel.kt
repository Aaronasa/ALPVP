package com.example.foodalp.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodalp.AppContainer
import com.example.foodalp.models.CreateRestaurantRequest
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.models.RestaurantResponse
import com.example.foodalp.models.UpdateRestaurantRequest
import com.example.foodalp.repositories.RestaurantRepository
import com.example.foodalp.ui.state.RestaurantState
import com.example.foodalp.ui.state.RestaurantUIState
//import com.example.foodalp.uistates.RestaurantByIdState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File


class RestaurantViewModel() : ViewModel() {


    private val repository = RestaurantRepository(AppContainer.restaurantService)
    private val _uiState = MutableStateFlow(RestaurantUIState())
    val uiState: StateFlow<RestaurantUIState>
        get() {
            return _uiState.asStateFlow()
        }

    private val _Restaurant = MutableStateFlow<List<RestaurantModel>>(emptyList())
    val Restaurant = _Restaurant

    private val _UIState = MutableStateFlow<RestaurantState>(RestaurantState.Start)
    val UIstate : StateFlow<RestaurantState> = _UIState

    private val _RestaurantById = MutableStateFlow<RestaurantModel?>(null)
    val RestaurantById : StateFlow<RestaurantModel?> = _RestaurantById


//    private val _RestaurantByIdState = MutableStateFlow<RestaurantByIdState>(RestaurantByIdState.Start)
//    val RestaurantByIdState : StateFlow<RestaurantByIdState> = _RestaurantByIdState



    fun fetchAllRestaurants(token: String) {
        viewModelScope.launch {
            _UIState.value = RestaurantState.Loading
            try {
                val FetchRestaurant = repository.getAllRestaurants(token)
                Log.d("ViewModel", "Fetched restaurants: $FetchRestaurant")
                _Restaurant.value = FetchRestaurant
                _UIState.value = RestaurantState.Success(FetchRestaurant)
            } catch (e: Exception) {
                _UIState.value = RestaurantState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun FetchRestaurantById(token: String, restaurantId: Int, callback: (RestaurantModel?) -> Unit){
        viewModelScope.launch {
            _UIState.value = RestaurantState.Loading
            try {
                val FetchRestaurantById = repository.getRestaurantById(token, restaurantId)
                Log.d("ViewModel", "Fetched restaurants: $FetchRestaurantById")
                _RestaurantById.value = FetchRestaurantById
                _UIState.value = RestaurantState.Success(listOf(FetchRestaurantById!!))
                callback(FetchRestaurantById)
            } catch (e: Exception) {
                _UIState.value = RestaurantState.Failed(e.message ?: "Unknown error")
            }
        }
    }


//    fun fetchRestaurantById(token: String, restaurantId: Int, callback: (RestaurantModel?) -> Unit) {
//        Log.d("ViewModel", "Fetching restaurant by id sebelum masuk launch: $restaurantId")
//        viewModelScope.launch {
//            Log.d("ViewModel", "Fetching restaurant by id setelah masuk launch: $restaurantId")
//            try {
//                Log.d("ViewModel", "Fetching restaurant by id sebelum dikirim ke repository: $restaurantId")
//                val response = repository.getRestaurantById(token, restaurantId)
//                Log.d("viemodelModel", "Fetching restaurant by id setelah masuk ke repository: $response")
//                callback(response)
//            } catch (e: Exception) {
//                Log.e("ViewModel", "Error fetching restaurant by id: ${e.message}", e)
//                callback(null)
//            }
//        }
//    }

    fun createRestaurant(token: String, context: Context, request: CreateRestaurantRequest) {
        viewModelScope.launch {
            try {
                Log.d("ViewModel", "Sending createRestaurant request: $request")
                // Ensure the image is not null
                val imagePart = request.image ?: throw Exception("Image is required for creating a restaurant") // Throw an exception if the image is null
                // Create the restaurant with the image part
                repository.createRestaurant(token, request.copy(image = imagePart), context)
                Log.d("ViewModel", "Restaurant created successfully")
                fetchAllRestaurants(token) // Refresh after creation
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
        val requestBody =
            file.asRequestBody("image/jpeg".toMediaTypeOrNull()) // Adjust content type accordingly
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

    fun updateRestaurant(request: UpdateRestaurantRequest, token: String) {
        viewModelScope.launch {
            try {
                repository.updateRestaurant(request)
                fetchAllRestaurants(token) // Pass the token to fetchAllRestaurants
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun deleteRestaurant(id: Int, token: String) {
        viewModelScope.launch {
            try {
                repository.deleteRestaurant(id)
                fetchAllRestaurants(token) // Pass the token to fetchAllRestaurants
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }


}
