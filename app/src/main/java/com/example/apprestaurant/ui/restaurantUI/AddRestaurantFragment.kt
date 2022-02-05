package com.example.apprestaurant.ui.restaurantUI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.apprestaurant.R
import com.example.apprestaurant.databinding.FragmentAddRestaurantBinding
import com.example.apprestaurant.db.RestaurantDB
import com.example.apprestaurant.db.entities.RestaurantEntity
import com.example.apprestaurant.network.RetrofitConfig
import com.example.apprestaurant.network.request.RestaurantRequest
import com.example.apprestaurant.network.response.toModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.*

class AddRestaurantFragment : Fragment() {

    private var _binding: FragmentAddRestaurantBinding? = null
    private val binding get() = _binding!!
    private var rest : RestaurantEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {
            addRest(binding.restName.toString())
            val action = AddRestaurantFragmentDirections.addToList()
            findNavController().navigate(action)
        }
    }

    private fun addRest(name: String) {
        RetrofitConfig.service
            .addRest(RestaurantRequest(1, name, binding.foodType.toString(), binding.rating.toString()))
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {

                        Toast.makeText(context,"Restaurant added successfully", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(context,"Sorry, we couldn't add the restaurant. Try again latter",
                            Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    rest = RestaurantEntity(binding.restName.text.toString(), binding.foodType.text.toString(), binding.rating.text.toString())
                    RestaurantDB.getInstance(requireContext()).restaurantDao().addRest(rest!!)
                    Toast.makeText(context,"Restaurant added locally", Toast.LENGTH_SHORT).show()
                    Log.e("Network", "Error ${t.localizedMessage}", t)
                }
            })
    }
}