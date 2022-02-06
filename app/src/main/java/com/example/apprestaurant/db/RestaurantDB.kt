package com.example.apprestaurant.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.apprestaurant.db.entities.DeliveryEntity
import com.example.apprestaurant.db.entities.RestaurantEntity

@Database(entities = [RestaurantEntity::class, DeliveryEntity::class], version = 1)
abstract class RestaurantDB : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDAO
    abstract fun deliveryDao(): DeliveryDAO

    companion object {
        @Volatile
        private var INSTANCE: RestaurantDB? = null

        fun getInstance(context: Context): RestaurantDB = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, RestaurantDB::class.java, "restaurant.db")
                .allowMainThreadQueries()
                .build()
    }
}