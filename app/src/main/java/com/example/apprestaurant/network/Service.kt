package com.example.apprestaurant.network

import com.example.apprestaurant.network.request.RestaurantRequest
import com.example.apprestaurant.network.response.RestaurantResponse
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @GET("restaurant")
    fun getRests(): Call<RestaurantResponse>

    @POST("addrestaurant")
    fun addRest(@Body req: RestaurantRequest): Call<Void>

    @DELETE("deleterestaurant/{restId}")
    fun delRest(@Path("restId") restId: Int): Call<Void>

}