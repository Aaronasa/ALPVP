package com.example.foodalp.services

import android.util.Log
import com.example.foodalp.AppContainer
import com.example.foodalp.models.CreateRestaurantRequest
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.models.RestaurantResponse
import com.example.foodalp.models.UpdateRestaurantRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface RestaurantAPIService {

    @POST("restaurants")
    suspend fun addRestaurant(@Body restaurant: RestaurantModel)


    @GET("auth/restaurants/read")
    suspend fun getAllRestaurants(): RestaurantResponse

    @GET("auth/restaurants/read/{id}")
    suspend fun getRestaurantById(@Path("id") id: Int): RestaurantModel {
        // Log the incoming ID for debugging
        Log.d("RestaurantService", "Fetching restaurant with ID: $id")

        // Use AppContainer's restaurantService to make the API call
        val response = AppContainer.restaurantService.getRestaurantById(id)

        // Log the response data to debug
        Log.d("RestaurantService", "Received Response: $response")

        return response
    }

    @POST("auth/restaurants/create")
    @Multipart
    suspend fun createRestaurant(
        @Part("name") name: RequestBody,
        @Part("address") address: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part image: MultipartBody.Part
    ): RestaurantModel

    @PUT("auth/restaurants/update")
    suspend fun updateRestaurant(@Body request: UpdateRestaurantRequest): RestaurantModel


    @DELETE("auth/restaurants/delete")
    suspend fun deleteRestaurant(@Body id: Int): RestaurantModel
}
