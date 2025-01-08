package com.example.foodalp.uistates

import com.example.foodalp.models.RestaurantModel

sealed interface RestaurantByIdState {
    data class Success(val data: RestaurantModel): RestaurantByIdState
    object Start: RestaurantByIdState
    object Loading: RestaurantByIdState
    data class Failed(val errorMessage: String): RestaurantByIdState

}