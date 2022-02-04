package com.example.apprestaurant.model

import com.google.gson.annotations.SerializedName

data class RestaurantObject(
    @SerializedName("idRest")
    val idRest: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("foodType")
    val foodType: String,
    @SerializedName("rating")
    val rating: String,
    )