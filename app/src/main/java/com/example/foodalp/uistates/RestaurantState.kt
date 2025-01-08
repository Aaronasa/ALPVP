package com.example.foodalp.ui.state

import com.example.foodalp.models.RestaurantModel

sealed interface RestaurantState{
    data class Success(val data: List<RestaurantModel>): RestaurantState
    object Start: RestaurantState
    object Loading: RestaurantState
    data class Failed(val errorMessage: String): RestaurantState
}
