package com.example.apprestaurant.ui.restaurantUI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apprestaurant.R
import com.example.apprestaurant.databinding.FragmentRestaurantDetailBinding
import com.example.apprestaurant.db.RestaurantDB
import com.example.apprestaurant.network.RetrofitConfig
import com.example.apprestaurant.network.response.DeliveryResponse
import com.example.apprestaurant.network.response.RestaurantResponse
import com.example.apprestaurant.network.response.toMap
import com.example.apprestaurant.network.response.toModel
import com.example.apprestaurant.ui.restaurantUI.deliveryUI.DeliveryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantDetailFragment : Fragment() {

    private var _binding: FragmentRestaurantDetailBinding? = null
    private val binding get() = _binding!!

    private val args: RestaurantDetailFragmentArgs by navArgs()
    private var name: String = ""
    private var foodType: String = ""
    private var idRest: Int = 0
    private var rating: String = ""
    private val adapter: DeliveryAdapter = DeliveryAdapter {
        var idDelivery: Int = it.idDelivery
        var food: String = it.food
        var idRest: Int = it.idRestaurant
        var address: String = it.address
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.let {
            name = it.name
            foodType = it.foodType
            idRest = it.restId
            rating = it.rating
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDeliveries()
        binding.dlvRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.dlvRV.adapter = this.adapter
        binding.tvName.text = name
        binding.tvFoodType.text = foodType
        binding.tvRating.text = rating
        binding.btnAddDlv.setOnClickListener {
            val action = RestaurantDetailFragmentDirections.detailToAddDelivery(
                idRest
            )
            findNavController().navigate(action)
        }
    }

    private fun getDeliveries() {
        RetrofitConfig.service.getDeliveries().enqueue(object : Callback<DeliveryResponse> {
            override fun onResponse(call: Call<DeliveryResponse>, response: Response<DeliveryResponse>) {
                if (response.isSuccessful) {
                    val dlv = response.body()
                    adapter.submitList(dlv?.data.toMap())
                } else {
                    Log.e("Network", "error en la conexion")
                    //get from db and refresh
                    val dlvEntity = RestaurantDB.getInstance(requireContext()).deliveryDao().findAllDeliveries()
                    adapter.submitList(dlvEntity.toModel())

                }
            }
            override fun onFailure(call: Call<DeliveryResponse>, t: Throwable) {
                Log.e("Network", "error en la conexion", t)
                Toast.makeText(context, "error de conexion", Toast.LENGTH_SHORT).show()
                // get all from db and refresh list
                val dlvEntity = RestaurantDB.getInstance(requireContext()).deliveryDao().findAllDeliveries()
                adapter.submitList(dlvEntity.toModel())

            }
        })
    }
}