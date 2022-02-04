package com.example.apprestaurant.db.entities

import androidx.room.*

@Entity(tableName = "restaurant")
data class RestaurantEntity (
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "foodType")
    val foodType: String,
    @ColumnInfo(name = "rating")
    val rating: Double,
    @PrimaryKey(autoGenerate = true)
    val idRest: Int? = null
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