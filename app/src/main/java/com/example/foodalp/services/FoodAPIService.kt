package com.example.foodalp.services

import android.util.Log
import com.example.foodalp.AppContainer
import com.example.foodalp.models.ApiRespoonse
import com.example.foodalp.models.CreateFoodRequest
import com.example.foodalp.models.FoodModel
import com.example.foodalp.models.GetAllFoodsResponse
import com.example.foodalp.models.UpdateFoodRequest
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

interface FoodAPIService {

    @GET("auth/food/readall")
    suspend fun getAllFoods(
        @Header("x-API-Token") token: String
    ): GetAllFoodsResponse {
        Log.d("FoodService", "Fetching all foods...")
        val response = AppContainer.foodService.getAllFoods(token)
        Log.d("FoodService", "Successfully fetched foods: ${response.data}")
        return response
    }

    @GET("auth/food/read/{id}")
    suspend fun getFoodDetail(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): Response<ApiRespoonse<FoodModel>>

    @POST("auth/food/create")
    @Multipart
    suspend fun createFood(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody
    ): FoodModel

    @GET("admin/food/readall")
    suspend fun getAllFoodsAdmin(
        @Header("x-API-Token") token: String
    ): GetAllFoodsResponse {
        Log.d("FoodService", "Fetching all foods (admin)...")
        val response = AppContainer.foodService.getAllFoodsAdmin(token)
        Log.d("FoodService", "Successfully fetched foods (admin): ${response.data}")
        return response
    }

    @GET("admin/food/read/{id}")
    suspend fun getFoodDetailAdmin(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): Response<ApiRespoonse<FoodModel>>

    @POST("admin/food/create")
    @Multipart
    suspend fun createFoodAdmin(
        @Header("x-API-Token") token: String,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part image: MultipartBody.Part
    ): FoodModel

    @PUT("admin/food/update/{id}")
    @Multipart
    suspend fun updateFood(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int,
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("price") price: RequestBody?,
        @Part image: MultipartBody.Part?
    ): FoodModel

    @DELETE("admin/food/delete/{id}")
    suspend fun deleteFood(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): FoodModel
}
