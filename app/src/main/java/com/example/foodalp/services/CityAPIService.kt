package com.example.foodalp.services

import com.example.foodalp.AppContainer
import com.example.foodalp.models.ApiRespoonse
import com.example.foodalp.models.CityModel
import com.example.foodalp.models.CityResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
//import okhttp3.Response
import retrofit2.http.*
import retrofit2.Response


interface CityAPIService {

    @GET("auth/city/readall")
    suspend fun getAllCity(
        @Header("x-API-Token") token: String
    ): CityResponse {
        val response = AppContainer.cityService.getAllCity(token)
        return response
    }

    @GET("auth/city/{id}")
    suspend fun getCityById(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): Response<ApiRespoonse<CityModel>>

    @POST("auth/city/create")
    @Multipart
    suspend fun createCity(
        @Header("x-API-Token") token: String,
        @Part("name") name: RequestBody,
        @Part image: MultipartBody.Part
    ): CityModel

    @PUT("auth/city/update/{id}")
    @Multipart
    suspend fun updateCity(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int,
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody
    ): CityModel

    @DELETE("auth/city/delete/{id}")
    suspend fun deleteCity(
        @Header("x-API-Token") token: String,
        @Path("id") id: Int
    ): CityModel
}
