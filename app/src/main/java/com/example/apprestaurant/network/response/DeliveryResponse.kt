package com.example.apprestaurant.network.response

import com.google.gson.annotations.Expose

data class DeliveryResponse(
    @Expose
    val data: MutableList<Delivery>
)