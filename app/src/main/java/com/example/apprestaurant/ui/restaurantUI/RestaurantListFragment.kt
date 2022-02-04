package com.example.apprestaurant.ui.restaurantUI

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apprestaurant.R
import com.example.apprestaurant.databinding.FragmentRestaurantListBinding
import com.example.apprestaurant.db.RestaurantDB
import com.example.apprestaurant.network.RetrofitConfig
import com.example.apprestaurant.network.response.RestaurantResponse
import com.example.apprestaurant.network.response.toEntity
import com.example.apprestaurant.network.response.toMap
import com.example.apprestaurant.network.response.toModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantListFragment : Fragment() {

    private var _binding: FragmentRestaurantListBinding? = null
    private val binding get() = _binding!!
    private var idRest: Int = 0


    private val adapter: RestaurantAdapter = RestaurantAdapter({
        var name: String = it.name
        var foodType: String = it.foodType
        var rating: String = it.rating.toString()
        idRest = it.idRest

        val action = RestaurantListFragmentDirections.listToDetail(
            it.name,
            it.foodType,
            it.rating.toString()
        )

        findNavController().navigate(action)
    }, {
        //deleteRestaurant()
    })

    private fun getRests() {
        RetrofitConfig.service.getRests().enqueue(object : Callback<RestaurantResponse> {
            override fun onResponse(call: Call<RestaurantResponse>, response: Response<RestaurantResponse>) {
                if (response.isSuccessful) {
                    val rest = response.body()
                    //get from api and add to db
                    RestaurantDB.getInstance(requireContext()).restaurantDao().addRest(rest?.data.toEntity())
                    //refresh all list
                    adapter.submitList(rest?.data.toMap())
                } else {
                    Log.e("Network", "error en la conexion")
                    //get from db and refresh
                    val restEntity = RestaurantDB.getInstance(requireContext()).restaurantDao().findAll()
                    adapter.submitList(restEntity.toModel())

                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                Log.e("Network", "error en la conexion", t)
                Toast.makeText(context, "error de conexion", Toast.LENGTH_SHORT).show()
                // get all from db and refresh list
                val restEntity = RestaurantDB.getInstance(requireContext()).restaurantDao().findAll()
                adapter.submitList(restEntity.toModel())

            }
        })
    }

    private fun delRest() {
        RetrofitConfig.service
            .delRest(idRest)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        getRests()
                        Log.d("Network", "restaurant deleted")
                        Toast.makeText(
                            context,
                            "Restaurant deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Log.d("Network", " network error")
                        Toast.makeText(
                            context,
                            "Sorry, we couldn't delete the restaurant. Try again latter",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(context, "Error deleting restaurant", Toast.LENGTH_SHORT).show()
                    Log.e("Network", "Error ${t.localizedMessage}", t)
                    //delete from db
                    val rest = RestaurantDB.getInstance(requireContext()).restaurantDao().findRestById(idRest)
                    RestaurantDB.getInstance(requireContext()).restaurantDao().deleteRest(rest)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRestaurantListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.restaurantRv.layoutManager = GridLayoutManager(context, 1)
        binding.restaurantRv.adapter= this.adapter
        // getRestaurants()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}