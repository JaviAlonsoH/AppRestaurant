package com.example.apprestaurant.network.response

import com.example.apprestaurant.db.entities.DeliveryEntity
import com.example.apprestaurant.db.entities.RestaurantEntity
import com.example.apprestaurant.model.DeliveryObject
import com.example.apprestaurant.model.RestaurantObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Delivery (
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

fun Delivery.toDeliveryObjectExt(): DeliveryObject {
    return DeliveryObject(idDelivery, food, idDelivery, address)
}

fun List<Delivery>?.toMap(): List<DeliveryObject> {
    return this?.map { it.toDeliveryObjectExt() } ?: emptyList()
}


fun Delivery.toEntity(): DeliveryEntity {
    return DeliveryEntity(food, idRestaurant, address, idDelivery)
}

fun List<Delivery>?.toEntity(): List<DeliveryEntity> {
    return this?.map { it.toEntity() } ?: emptyList()
}

fun DeliveryEntity.toModel(): DeliveryObject {
    return DeliveryObject(idDelivery!!, food, idRestaurant, address)
}

fun List<DeliveryEntity>?.toModel(): List<DeliveryObject> {
    return this?.map { it.toModel() } ?: emptyList()
}