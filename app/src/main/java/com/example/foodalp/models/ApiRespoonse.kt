package com.example.foodalp.models

data class ApiRespoonse<T> (
    val message: String,
    val data:T
)