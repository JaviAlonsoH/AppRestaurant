package com.example.apprestaurant.model

import com.google.gson.annotations.SerializedName
import java.sql.Time

data class DeliveryObject(
    @SerializedName("idDelivery")
    val idDelivery: Int,
    @SerializedName("food")
    val food: String,
    @SerializedName("idRestaurant")
    val idRestaurant: Int,
    @SerializedName("address")
    val address: String,
)