package com.example.foodalp.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

import com.example.foodalp.models.CreateFoodRequest
import com.example.foodalp.models.FoodModel
import com.example.foodalp.models.GetAllFoodsResponse
import com.example.foodalp.models.UpdateFoodRequest

interface FoodAPIService {

    @Multipart
    @POST("/food/create")
    fun createFood(
        @Header("x-API-Token") token: String,
        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody
    ): Call<CreateFoodRequest>

    @GET("/food/read/{id}")
    fun getFoodDetail(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): Call<FoodModel>

    @GET("/food/readall")
    fun getAllFoods(
        @Header("x-API-Token") token: String
    ): Call<GetAllFoodsResponse>

    @Multipart
    @PUT("/food/update")
    fun updateFood(
        @Header("x-API-Token") token: String,
        @Part("id") id: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody
    ): Call<UpdateFoodRequest>
}