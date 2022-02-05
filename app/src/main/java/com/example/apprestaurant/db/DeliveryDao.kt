package com.example.apprestaurant.db

import androidx.room.*
import com.example.apprestaurant.db.entities.DeliveryEntity
import com.example.apprestaurant.db.entities.RestaurantEntity

@Dao
interface DeliveryDAO {

    @Query("SELECT * FROM delivery")
    fun findAllDeliveries(): List<DeliveryEntity>

    @Query("SELECT * FROM restaurant INNER JOIN delivery ON :idDelivery = restaurant.idRestaurant")
    fun findRestaurantByDeliveryId(idDelivery: Int) : RestaurantEntity

    @Query("SELECT * FROM delivery WHERE delivery.idDelivery = :idDelivery LIMIT 1")
    fun findDeliveryById(idDelivery: Int): DeliveryEntity

    @Query("SELECT * FROM delivery WHERE delivery.idRestaurant = :idRestaurant")
    fun findDeliveryByRestaurant(idRestaurant: Int) : List<DeliveryEntity>

    @Insert
    fun addDelivery(deliveryEntity: DeliveryEntity)

    @Delete
    fun deleteDelivery(deliveryEntity: DeliveryEntity)

}