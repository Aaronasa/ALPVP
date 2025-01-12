package com.example.foodalp.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodalp.AppContainer
import com.example.foodalp.models.*
import com.example.foodalp.repositories.FoodRepository
import com.example.foodalp.uistates.FoodState
import com.example.foodalp.uistates.FoodUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FoodViewModel : ViewModel() {

    private val repository = FoodRepository(AppContainer.foodService)
    private val _uiState = MutableStateFlow(FoodUIState())
    val uiState: StateFlow<FoodUIState>
        get() = _uiState.asStateFlow()

    private val _foodList = MutableStateFlow<List<FoodModel>>(emptyList())
    val foodList: StateFlow<List<FoodModel>> = _foodList

    private val _foodById = MutableStateFlow<FoodModel?>(null)
    val foodById: StateFlow<FoodModel?> = _foodById

    private val _foodState = MutableStateFlow<FoodState>(FoodState.Start)
    val foodState: StateFlow<FoodState> = _foodState

    fun fetchAllFoods(token: String) {
        viewModelScope.launch {
            _foodState.value = FoodState.Loading
            try {
                val fetchedFoods = repository.getAllFoods(token)
                Log.d("FoodViewModel", "Fetched foods: $fetchedFoods")
                _foodList.value = fetchedFoods
                _foodState.value = FoodState.Success(fetchedFoods)
            } catch (e: Exception) {
                Log.e("FoodViewModel", "Error fetching foods: ${e.message}", e)
                _foodState.value = FoodState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchFoodById(token: String, foodId: Int) {
        viewModelScope.launch {
            _foodState.value = FoodState.Loading
            try {
                Log.d("FoodViewModel", "Fetching food by ID: $foodId")
                val food = repository.getFoodDetail(token, foodId)
                _foodById.value = food
                _foodState.value = FoodState.Success(listOf(food))
                Log.d("FoodViewModel", "Fetched food: $food")
            } catch (e: Exception) {
                Log.e("FoodViewModel", "Error fetching food: ${e.message}", e)
                _foodState.value = FoodState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun createFood(token: String, context: Context, name: String, description: String, price: String, imageUri: Uri) {
        viewModelScope.launch {
            try {
                Log.d("FoodViewModel", "Sending createFood request: name=$name, description=$description, price=$price")

                // Convert image URI to MultipartBody.Part
                val imagePart = createImagePart(context, imageUri)

                repository.createFood(token, imagePart, name, description, price)
                Log.d("FoodViewModel", "Food created successfully")

                fetchAllFoods(token) // Refresh food list
                _uiState.value = _uiState.value.copy(errorMessage = null)
            } catch (e: Exception) {
                Log.e("FoodViewModel", "Error creating food: ${e.message}", e)
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun updateFood(token: String, id: Int, context: Context, name: String?, description: String?, price: String?, imageUri: Uri?) {
        viewModelScope.launch {
            try {
                Log.d("FoodViewModel", "Updating food with ID: $id")

                // Convert image URI to MultipartBody.Part if not null
                val imagePart = imageUri?.let { createImagePart(context, it) }

                repository.updateFood(token, id, imagePart, name, description, price)
                fetchAllFoods(token) // Refresh food list after update
                _uiState.value = _uiState.value.copy(errorMessage = null)
            } catch (e: Exception) {
                Log.e("FoodViewModel", "Error updating food: ${e.message}", e)
                _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Failed to update food")
            }
        }
    }

    private fun createImagePart(context: Context, imageUri: Uri): MultipartBody.Part {
        val file = File(getRealPathFromURI(context, imageUri))
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestBody)
    }

    private fun getRealPathFromURI(context: Context, uri: Uri): String? {
        var filePath: String? = null
        if (uri.scheme == "content") {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver.query(uri, proj, null, null, null)
            cursor?.use {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                it.moveToFirst()
                filePath = it.getString(columnIndex)
            }
        } else if (uri.scheme == "file") {
            filePath = uri.path
        }
        return filePath
    }
}
