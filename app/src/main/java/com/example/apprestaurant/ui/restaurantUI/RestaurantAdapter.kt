package com.example.apprestaurant.ui.restaurantUI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apprestaurant.databinding.RestaurantItemBinding
import com.example.apprestaurant.model.RestaurantObject
import androidx.recyclerview.widget.ListAdapter

class RestaurantAdapter (
    private val onRestaurantClick: (RestaurantObject) -> Unit,
    private val onDeleteClickListener: (RestaurantObject) -> Unit,
        ):
        androidx.recyclerview.widget.ListAdapter<RestaurantObject, RestaurantAdapter.ViewHolder>(DiffUtilCallback) {

            inner class ViewHolder(var binding: RestaurantItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RestaurantItemBinding = RestaurantItemBinding.inflate(layoutInflater, parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = getItem(position)
        holder.binding.restRating.text = restaurant.rating.toString()
        holder.binding.restName.text = restaurant.name
        holder.binding.restFoodtype.text = restaurant.foodType
        holder.binding.delButton.setOnClickListener {
            onDeleteClickListener(restaurant)
        }
        holder.binding.root.setOnClickListener {
            onRestaurantClick(restaurant)
        }
    }



}


private object DiffUtilCallback : DiffUtil.ItemCallback<RestaurantObject>() {

    override fun areItemsTheSame(
        oldItem: RestaurantObject,
        newItem: RestaurantObject
    ): Boolean {
        return oldItem.idRest == newItem.idRest
    }

    override fun areContentsTheSame(
        oldItem: RestaurantObject,
        newItem: RestaurantObject
    ): Boolean {
        return oldItem.idRest == newItem.idRest
    }
}