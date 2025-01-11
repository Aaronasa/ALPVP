package com.example.foodalp.uistates


import com.example.foodalp.models.CityModel

data class CityUIState(
    val isLoading: Boolean = false,
    val cities: List<CityModel> = emptyList(),
    val errorMessage: String? = null
)
