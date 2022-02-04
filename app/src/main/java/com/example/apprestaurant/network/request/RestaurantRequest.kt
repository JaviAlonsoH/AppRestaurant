package com.example.apprestaurant.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

data class RestaurantRequest(
    @SerializedName("idRest")
    @Expose
    val idRest: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("foodType")
    @Expose
    val foodType: String,
    @SerializedName("rating")
    val rating: String
)