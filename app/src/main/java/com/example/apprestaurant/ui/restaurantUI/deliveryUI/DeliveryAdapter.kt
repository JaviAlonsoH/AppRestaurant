package com.example.apprestaurant.ui.restaurantUI.deliveryUI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apprestaurant.databinding.DeliveryItemBinding
import com.example.apprestaurant.model.DeliveryObject
import com.example.apprestaurant.model.RestaurantObject

class DeliveryAdapter(
    private val onDeleteClickListener: (DeliveryObject) -> Unit,
    private val onUpdateClickListener: (DeliveryObject) -> Unit
    ):
    ListAdapter<DeliveryObject, DeliveryAdapter.ViewHolder>(DiffUtilCallbackDelivery) {

    inner class ViewHolder(var binding: DeliveryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: DeliveryItemBinding =
            DeliveryItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val delivery = getItem(position)
        holder.binding.tvIdDelivery.text = delivery.idDelivery.toString()
        holder.binding.tvDeliveryFood.text = delivery.food

        holder.binding.btnDelDelivery.setOnClickListener {
            onDeleteClickListener(delivery)
        }

        holder.binding.btnEditDelivery.setOnClickListener {
            onUpdateClickListener(delivery)
        }
    }
}


private object DiffUtilCallbackDelivery : DiffUtil.ItemCallback<DeliveryObject>() {

    override fun areItemsTheSame(
        oldItem: DeliveryObject,
        newItem: DeliveryObject
    ): Boolean {
        return oldItem.idDelivery == newItem.idDelivery
    }

    override fun areContentsTheSame(
        oldItem: DeliveryObject,
        newItem: DeliveryObject
    ): Boolean {
        return oldItem.idDelivery == newItem.idDelivery
    }
}