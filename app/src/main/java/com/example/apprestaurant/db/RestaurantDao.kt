package com.example.apprestaurant.db

import androidx.room.*
import com.example.apprestaurant.db.entities.RestaurantEntity

@Dao
interface RestaurantDAO {

    @Query("SELECT * FROM restaurant")
    fun findAll(): List<RestaurantEntity>

    @Query("SELECT * FROM restaurant WHERE restaurant.idRest = :idRest LIMIT 1")
    fun findRestById(idRest: Int): RestaurantEntity

    @Query("SELECT * FROM restaurant WHERE restaurant.name LIKE :query")
    fun findRestByName(query: String): RestaurantEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRest(restsEntities: RestaurantEntity)

    @Delete
    fun deleteRest(restEntity: RestaurantEntity)

}