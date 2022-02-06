package com.example.apprestaurant.db.entities

import androidx.room.*

@Entity(tableName = "restaurant")
data class RestaurantEntity (
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "foodType")
    val foodType: String,
    @ColumnInfo(name = "rating")
    val rating: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idRestaurant")
    val idRest: Int? = null
)

data class FoodType(
    val idRest: Int?,
    val foodType: String,
    val rating: String

)
/*
data class RestaurantDeliveries (
    @Embedded
    val restEntity: RestEntity,
    @Relation(
        parentColumn = "idRest",
        entityColumn = "idDelivery"
    )
    val deliveryList: List<DeliveryEntity>
)

 */