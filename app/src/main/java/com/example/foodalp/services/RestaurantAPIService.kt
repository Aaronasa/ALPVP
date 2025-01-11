package com.example.foodalp.services

import android.util.Log
import com.example.foodalp.AppContainer
import com.example.foodalp.models.ApiRespoonse
import com.example.foodalp.models.CreateRestaurantRequest
import com.example.foodalp.models.RestaurantModel
import com.example.foodalp.models.RestaurantResponse
import com.example.foodalp.models.UpdateRestaurantRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path


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
    ): Response<ApiRespoonse<RestaurantModel>>

    @POST("auth/restaurants/create")
    @Multipart
    suspend fun createRestaurant(
        @Header("x-API-Token") token: String,
        @Part("name") name: RequestBody,
        @Part("address") address: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part image: MultipartBody.Part
    ): RestaurantModel

    @GET("admin/restaurants/read")
    suspend fun getAllRestaurantsAdmin(
        @Header("x-API-Token") token: String
    ): RestaurantResponse {
        Log.d("RestaurantService", "Fetching all restaurants...")
        val response = AppContainer.restaurantService.getAllRestaurantsAdmin(token)
        Log.d("RestaurantService", "Successfully fetched restaurants: ${response.data}")
        return response
    }

    @GET("admin/restaurants/read/{id}")
    suspend fun getRestaurantByIdAdmin(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): Response<ApiRespoonse<RestaurantModel>>

    @POST("admin/restaurants/create")
    @Multipart
    suspend fun createRestaurantAdmin(
        @Header("x-API-Token") token: String,
        @Part("name") name: RequestBody,
        @Part("address") address: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part image: MultipartBody.Part
    ): RestaurantModel


    @PUT("admin/restaurants/update/{id}")
    @Multipart
    suspend fun updateRestaurant(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int,
        @Part("name") name: RequestBody?,
        @Part("address") address: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part image: MultipartBody.Part?
    ): RestaurantModel

    @DELETE("admin/restaurants/delete/{id}")
    suspend fun deleteRestaurant(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): RestaurantModel
}
