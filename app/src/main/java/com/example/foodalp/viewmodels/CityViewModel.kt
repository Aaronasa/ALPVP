package com.example.foodalp.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodalp.AppContainer
import com.example.foodalp.models.CityModel
import com.example.foodalp.models.CreateCityRequest
import com.example.foodalp.models.CreateRestaurantRequest
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.models.RestaurantResponse
import com.example.foodalp.models.UpdateCityRequest
import com.example.foodalp.models.UpdateRestaurantRequest
import com.example.foodalp.repositories.CityRepository
import com.example.foodalp.repositories.RestaurantRepository
import com.example.foodalp.uistates.CityState
import com.example.foodalp.uistates.CityUIState
import com.example.foodalp.uistates.RestaurantState
import com.example.foodalp.uistates.RestaurantUIState
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


class CityViewModel() : ViewModel() {


    private val repository = CityRepository(AppContainer.cityService)
    private val _uiState = MutableStateFlow(CityUIState())
    val uiState: StateFlow<CityUIState>
        get() {
            return _uiState.asStateFlow()
        }

    private val _City = MutableStateFlow<List<CityModel>>(emptyList())
    val City = _City

    private val _UIState = MutableStateFlow<CityState>(CityState.Start)
    val UIstate: StateFlow<CityState> = _UIState

    private val _CityById = MutableStateFlow<CityModel?>(null)
    val CityById: StateFlow<CityModel?> = _CityById

    private val _cityState = MutableLiveData<CityState>()
    val cityState: LiveData<CityState> get() = _cityState

    fun fetchAllCities(token: String) {
        viewModelScope.launch {
            _UIState.value = CityState.Loading
            try {
                val FetchCity = repository.getAllCity(token)
                Log.d("ViewModel", "Fetched restaurants: $FetchCity")
                _City.value = FetchCity
                _UIState.value = CityState.Success(FetchCity)
            } catch (e: Exception) {
                _UIState.value = CityState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun FetchCityById(token: String, cityId: Int, callback: (CityModel?) -> Unit) {
        viewModelScope.launch {
            _UIState.value = CityState.Loading
            try {
                // Fetch the restaurant by ID
                val response = repository.getCityById(token, cityId)
                Log.d("ViewModel", "Fetched restaurant: $response")
                _CityById.value = response
                _UIState.value = CityState.Success(listOf(response!!))
                callback(response)
            } catch (e: Exception) {
                // Handle exceptions
                Log.e("ViewModel", "Error fetching restaurant: ${e.message}", e)
                _UIState.value = CityState.Failed(e.message ?: "Unknown error")
                callback(null)
            }
        }
    }


    fun createCity(token: String, context: Context, request: CreateCityRequest) {
        viewModelScope.launch {
            try {
                Log.d("ViewModel", "Sending createRestaurant request: $request")
                // Ensure the image is not null
                val imagePart = request.image
                    ?: throw Exception("Image is required for creating a restaurant") // Throw an exception if the image is null
                // Create the restaurant with the image part
                repository.createCity(token, request.copy(image = imagePart), context)
                Log.d("ViewModel", "Restaurant created successfully")
                fetchAllCities(token) // Refresh after creation
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


    fun updateCity(token: String, id: Int, name: String,context: Context, imageUri: Uri?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                Log.d("CityViewModel", "Updating city with ID: $id, Name: $name")
                val updatedCity = repository.updateCity(token, id, name, imageUri, context)
                Log.d("CityViewModel", "City updated successfully: $updatedCity")
                fetchAllCities(token) // Refresh city list after update
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = null)
            } catch (e: Exception) {
                Log.e("CityViewModel", "Error updating city: ${e.message}", e)
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }



    private val _deleteCityResult = MutableLiveData<Result<CityModel?>>()
    val deleteCityResult: LiveData<Result<CityModel?>>
        get() = _deleteCityResult

    fun deleteCity(token: String, id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val result = repository.deleteCity(token, id)
                if (result != null) {
                    onSuccess()
                } else {
                    onError("City not found")
                }
            } catch (e: Exception) {
                Log.e("CityViewModel", "Error deleting city: ${e.message}", e)
                onError(e.message ?: "An error occurred")
            }
        }
    }


}
