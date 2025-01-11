package com.example.foodalp.uistates


import com.example.foodalp.models.CityModel

sealed interface CityState{
    data class Success(val data: List<CityModel>): CityState
    object Start: CityState
    object Loading: CityState
    data class Failed(val errorMessage: String): CityState
}