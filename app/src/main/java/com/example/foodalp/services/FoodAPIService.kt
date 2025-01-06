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
    @POST("api/food/create")
    fun createFood(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody
    ): Call<CreateFoodRequest>

    @GET("api/food/read/{id}")
    fun getFoodDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<FoodModel>

    @GET("api/food/readall")
    fun getAllFoods(
        @Header("Authorization") token: String
    ): Call<GetAllFoodsResponse>

    @Multipart
    @PUT("api/food/update")
    fun updateFood(
        @Header("Authorization") token: String,
        @Part("id") id: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody
    ): Call<UpdateFoodRequest>
}