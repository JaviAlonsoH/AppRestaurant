package com.example.apprestaurant.network.response

import com.google.gson.annotations.Expose

data class RestaurantResponse (
    @Expose
    val data: MutableList<Restaurant>
)
