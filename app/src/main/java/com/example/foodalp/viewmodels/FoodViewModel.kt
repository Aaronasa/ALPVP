package com.example.foodalp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodalp.models.*
import com.example.foodalp.repositories.FoodRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class FoodViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    private val _createFoodResponse = MutableLiveData<Response<CreateFoodResponse>>()
    val createFoodResponse: LiveData<Response<CreateFoodResponse>> = _createFoodResponse

    private val _foodDetailResponse = MutableLiveData<Response<FoodModel>>()
    val foodDetailResponse: LiveData<Response<FoodModel>> = _foodDetailResponse

    private val _allFoodsResponse = MutableLiveData<Response<GetAllFoodsResponse>>()
    val allFoodsResponse: LiveData<Response<GetAllFoodsResponse>> = _allFoodsResponse

    private val _updateFoodResponse = MutableLiveData<Response<UpdateFoodRequest>>()
    val updateFoodResponse: LiveData<Response<UpdateFoodRequest>> = _updateFoodResponse

    private val _deleteFoodResponse = MutableLiveData<Response<Void>>()
    val deleteFoodResponse: LiveData<Response<Void>> = _deleteFoodResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun createFood(token: String, image: MultipartBody.Part, name: RequestBody, description: RequestBody, price: RequestBody) {
        viewModelScope.launch {
            try {
                val response = foodRepository.createFood(token, image,
                    name.toString(), description.toString(), price.toString()
                ).execute()  // Execute the Call to get Response
                if (response.isSuccessful) {
                    _createFoodResponse.postValue(response.body() as Response<CreateFoodResponse>?)
                } else {
                    _errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
            }
        }
    }

    fun getFoodDetail(token: String, id: Int) {
        viewModelScope.launch {
            try {
                val response = foodRepository.getFoodDetail(token, id).execute()  // Execute the Call
                if (response.isSuccessful) {
                    _foodDetailResponse.postValue(response.body() as Response<FoodModel>?)
                } else {
                    _errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
            }
        }
    }

    fun getAllFoods(token: String) {
        viewModelScope.launch {
            try {
                val response = foodRepository.getAllFoods(token).execute()  // Execute the Call
                if (response.isSuccessful) {
                    _allFoodsResponse.postValue(response.body() as Response<GetAllFoodsResponse>?)
                } else {
                    _errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
            }
        }
    }

    fun updateFood(token: String, id: RequestBody, image: MultipartBody.Part?, name: RequestBody, description: RequestBody, price: RequestBody) {
        viewModelScope.launch {
            try {
                val response = foodRepository.updateFood(token, id, image,
                    name.toString(), description.toString(), price.toString()
                ).execute()  // Execute the Call
                if (response.isSuccessful) {
                    _updateFoodResponse.postValue(response.body() as Response<UpdateFoodRequest>?)
                } else {
                    _errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
            }
        }
    }
}
