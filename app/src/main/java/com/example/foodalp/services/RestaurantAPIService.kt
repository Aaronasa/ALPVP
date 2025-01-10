package com.example.foodalp.services

import android.util.Log
import com.example.foodalp.AppContainer
import com.example.foodalp.models.CreateRestaurantRequest
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.models.RestaurantResponse
import com.example.foodalp.models.UpdateRestaurantRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
//import okhttp3.Response
import retrofit2.http.*
import retrofit2.Response


interface RestaurantAPIService {

    @GET("auth/restaurants/read")
    suspend fun getAllRestaurants(
        @Header("x-API-Token") token: String
    ): RestaurantResponse {
        Log.d("RestaurantService", "Fetching all restaurants...")
        val response = AppContainer.restaurantService.getAllRestaurants(token)
        Log.d("RestaurantService", "Successfully fetched restaurants: ${response.data}")
        return response
    }

    @GET("auth/restaurants/read/{id}")
    suspend fun getRestaurantById(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): Response<RestaurantModel>
//    {
//        Log.d("RestaurantService", "Fetching restaurant with ID: $id")
//        val response = AppContainer.restaurantService.getRestaurantById(token, id)
//        Log.d("RestaurantService", "Received Response: $response")
//        return response

//    }

    @POST("auth/restaurants/create")
    @Multipart
    suspend fun createRestaurant(
        @Header("x-API-Token") token: String,
        @Part("name") name: RequestBody,
        @Part("address") address: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part image: MultipartBody.Part
    ): RestaurantModel

    @PUT("auth/restaurants/update")
    suspend fun updateRestaurant(@Body request: UpdateRestaurantRequest): RestaurantModel


    @DELETE("auth/restaurants/delete")
    suspend fun deleteRestaurant(@Body id: Int): RestaurantModel
    fun getRestaurantById(string: kotlin.String)
}
