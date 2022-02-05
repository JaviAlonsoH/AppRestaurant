package com.example.apprestaurant.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeliveryRequest(
    @SerializedName("idDelivery")
    @Expose
    val idDelivery: Int,
    @SerializedName("food")
    @Expose
    val food: String,
    @SerializedName("idRestaurant")
    @Expose
    val idRestaurant: Int,
    @SerializedName("address")
    @Expose
    val address: String

)