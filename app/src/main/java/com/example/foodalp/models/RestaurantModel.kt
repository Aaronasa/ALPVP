    package com.example.foodalp.models

    import okhttp3.MultipartBody

//    // Response model for Restaurant-related API calls
//    data class RestaurantResponse(
//        val data: RestaurantModel? // If no restaurant is found, data can be null
//    )
    data class RestaurantResponse(
        val data: List<RestaurantModel> // `data` wraps the list of restaurants
    )

    // Model for individual restaurants
    data class RestaurantModel(
        val id: Int,
        val name: String,
        val address: String,
        val phone: String,
        var image: String
    )

    // Request model for creating a restaurant
    data class CreateRestaurantRequest(
        val name: String,
        val address: String,
        val phone: String,
        val image: MultipartBody.Part?
    )

    // Request model for updating a restaurant
    data class UpdateRestaurantRequest(
        val id: Int,
        val name: String? = null,
        val address: String? = null,
        val phone: String? = null,
        val image: MultipartBody.Part? = null
    )

    // Request model for deleting a restaurant
    data class DeleteRestaurantRequest(
        val id: Int
    )

    // Request model for reading a restaurant
    data class ReadRestaurantRequest(
        val id: Int? = null
    )

