package com.example.foodalp.repositories

import com.example.foodalp.models.*
import com.example.foodalp.services.FoodAPIService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Retrofit

class FoodRepository(private val foodAPIService: FoodAPIService) {

    fun createFood(token: String, image: MultipartBody.Part, name: String, description: String, price: String): Call<CreateFoodRequest> {
        val nameRequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionRequestBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val priceRequestBody = price.toRequestBody("text/plain".toMediaTypeOrNull())

        return foodAPIService.createFood(token, image, nameRequestBody, descriptionRequestBody, priceRequestBody)
    }

    suspend fun getFoodDetail(token: String, id: Int): Call<FoodModel> {
        return foodAPIService.getFoodDetail(token, id)
    }

    suspend fun getAllFoods(token: String): Call<GetAllFoodsResponse> {
        return foodAPIService.getAllFoods(token)
    }

    suspend fun updateFood(
        token: String,
        id: RequestBody,
        image: MultipartBody.Part?,
        name: String,
        description: String,
        price: String
    ): Call<UpdateFoodRequest> {
        val idBody = RequestBody.create("text/plain".toMediaTypeOrNull(), id.toString())
        val nameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val descriptionBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)
        val priceBody = RequestBody.create("text/plain".toMediaTypeOrNull(), price)

        return foodAPIService.updateFood(token, idBody, image, nameBody, descriptionBody, priceBody)
    }

    companion object {
        fun create(retrofit: Retrofit): FoodRepository {
            val service = retrofit.create(FoodAPIService::class.java)
            return FoodRepository(service)
        }
    }
}
