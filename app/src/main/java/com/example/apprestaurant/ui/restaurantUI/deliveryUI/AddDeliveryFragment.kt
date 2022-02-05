package com.example.apprestaurant.ui.restaurantUI.deliveryUI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.example.apprestaurant.R
import com.example.apprestaurant.databinding.FragmentAddDeliveryBinding
import com.example.apprestaurant.databinding.FragmentRestaurantDetailBinding
import com.example.apprestaurant.db.RestaurantDB
import com.example.apprestaurant.db.entities.DeliveryEntity
import com.example.apprestaurant.db.entities.RestaurantEntity
import com.example.apprestaurant.network.RetrofitConfig
import com.example.apprestaurant.network.request.DeliveryRequest
import com.example.apprestaurant.network.request.RestaurantRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDeliveryFragment : Fragment() {

    private var _binding: FragmentAddDeliveryBinding? = null
    private val binding get() = _binding!!
    private val args: AddDeliveryFragmentArgs by navArgs()
    private val idRest: Int = 0
    private var delivery: DeliveryEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.let {
            it.idRestaurant
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val delv = RestaurantDB.getInstance(requireContext()).restaurantDao().findRestById(args.idRestaurant)
        binding.btnSaveDelivery.setOnClickListener {
            addDelivery()
            val action = AddDeliveryFragmentDirections.addDlvToDetail(
                delv.name,
                delv.foodType,
                delv.rating,
                delv.idRest!!
            )
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun addDelivery() {
        RetrofitConfig.service
            .addDelivery(DeliveryRequest(1, binding.etFood.text.toString(), args.idRestaurant, binding.etAddress.text.toString()))
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context,"Delivery added successfully", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context,"Sorry, we couldn't add the restaurant. Try again latter",
                            Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    delivery = DeliveryEntity(binding.etFood.text.toString(), args.idRestaurant, binding.etAddress.text.toString())
                    RestaurantDB.getInstance(requireContext()).deliveryDao().addDelivery(delivery!!)
                    Toast.makeText(context,"Delivery added locally", Toast.LENGTH_SHORT).show()
                    Log.e("Network", "Error ${t.localizedMessage}", t)
                }
            })
    }

}