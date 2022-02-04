package com.example.apprestaurant.network.response

import com.example.apprestaurant.db.entities.RestaurantEntity
import com.example.apprestaurant.model.RestaurantObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Restaurant (
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

fun Restaurant.toRestObjectExt(): RestaurantObject {
    return RestaurantObject(idRest, name, foodType, rating)
}

fun List<Restaurant>?.toMap(): List<RestaurantObject> {
    return this?.map { it.toRestObjectExt() } ?: emptyList()
}


fun Restaurant.toEntity(): RestaurantEntity {
    return RestaurantEntity(name, foodType, rating, idRest)
}

fun List<Restaurant>?.toEntity(): List<RestaurantEntity> {
    return this?.map { it.toEntity() } ?: emptyList()
}

fun RestaurantEntity.toModel(): RestaurantObject {
    return RestaurantObject(idRest!!, name, foodType, rating)
}

fun List<RestaurantEntity>?.toModel(): List<RestaurantObject> {
    return this?.map { it.toModel() } ?: emptyList()
}