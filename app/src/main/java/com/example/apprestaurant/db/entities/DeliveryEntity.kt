package com.example.apprestaurant.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "delivery")
data class DeliveryEntity (
    @ColumnInfo(name = "food")
    val food: String,
    @ColumnInfo(name = "idRestaurant")
    val idRestaurant: Int,
    @ColumnInfo(name = "address")
    val address: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idDelivery")
    val idDelivery: Int? = null
        )