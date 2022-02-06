package com.example.apprestaurant.db

import androidx.room.*
import com.example.apprestaurant.db.entities.RestaurantEntity

@Dao
interface RestaurantDAO {

    @Query("SELECT * FROM restaurant")
    fun findAll(): List<RestaurantEntity>

    @Query("SELECT * FROM restaurant WHERE restaurant.idRestaurant = :idRestaurant LIMIT 1")
    fun findRestById(idRestaurant: Int): RestaurantEntity

    @Query("SELECT * FROM restaurant WHERE restaurant.name LIKE :query")
    fun findRestByName(query: String): RestaurantEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRest(restsEntities: RestaurantEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRests(restsEntities: List<RestaurantEntity>)

    @Update(entity = RestaurantEntity::class)
    fun updateRestaurant(restaurantEntity: RestaurantEntity)

    @Update
    fun updateRestaurants(restaurantEntities: List<RestaurantEntity>)

    @Delete
    fun deleteRest(restEntity: RestaurantEntity)

}