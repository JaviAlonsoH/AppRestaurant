package com.example.apprestaurant.ui.restaurantUI

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apprestaurant.MainActivity
import com.example.apprestaurant.R
import com.example.apprestaurant.databinding.FragmentRestaurantDetailBinding
import com.example.apprestaurant.db.RestaurantDB
import com.example.apprestaurant.db.entities.DeliveryEntity
import com.example.apprestaurant.network.RetrofitConfig
import com.example.apprestaurant.network.response.DeliveryResponse
import com.example.apprestaurant.network.response.RestaurantResponse
import com.example.apprestaurant.network.response.toMap
import com.example.apprestaurant.network.response.toModel
import com.example.apprestaurant.ui.restaurantUI.deliveryUI.DeliveryAdapter
import com.example.apprestaurant.ui.restaurantUI.deliveryUI.EditDeliveryFragment
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
    private var idDelivery: Int = 0

    private val adapter: DeliveryAdapter = DeliveryAdapter ({
        idDelivery = it.idDelivery
        it.food
        it.idRestaurant
        it.address
        deleteDelivery(idDelivery)
    }, {
        val action = RestaurantDetailFragmentDirections.detailToEditDelivery(
            idDelivery,
            idRest
        )
        findNavController().navigate(action)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.let {
            name = it.name
            foodType = it.foodType
            idRest = it.restId
            rating = it.rating
        }
        setHasOptionsMenu(true)
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
        binding.dlvRV.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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

        binding.btnEditRestaurant.setOnClickListener {
            val action = RestaurantDetailFragmentDirections.detailToEditRestaurant(
                idRest,
                name
            )
            findNavController().navigate(action)
        }

        binding.btnDeleteRestaurant.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Are you sure you want to delete this restaurant?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    deleteRestaurant()
                    val action = RestaurantDetailFragmentDirections.detailToList()
                    findNavController().navigate(action)
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

    private fun getDeliveries() {
        RetrofitConfig.service.getDeliveries().enqueue(object : Callback<DeliveryResponse> {
            override fun onResponse(
                call: Call<DeliveryResponse>,
                response: Response<DeliveryResponse>
            ) {
                if (response.isSuccessful) {
                    val dlv = response.body()
                    adapter.submitList(dlv?.data.toMap())
                } else {
                    Log.e("Network", "error en la conexion")
                    //get from db and refresh
                    val dlvEntity =
                        RestaurantDB.getInstance(requireContext()).deliveryDao().findAllDeliveries()
                    adapter.submitList(dlvEntity.toModel())

                }
            }

            override fun onFailure(call: Call<DeliveryResponse>, t: Throwable) {
                Log.e("Network", "error en la conexion", t)
                Toast.makeText(context, "error de conexion", Toast.LENGTH_SHORT).show()
                // get all from db and refresh list
                val dlvEntity =
                    RestaurantDB.getInstance(requireContext()).deliveryDao().findDeliveryByRestaurant(args.restId)
                adapter.submitList(dlvEntity.toModel())

            }
        })
    }

    private fun deleteDelivery(idDelivery: Int) {
        var delivery: DeliveryEntity = RestaurantDB.getInstance(requireContext()).deliveryDao().findDeliveryById(idDelivery)
        RestaurantDB.getInstance(requireContext()).deliveryDao().deleteDelivery(delivery)
        var deliveries: List<DeliveryEntity> = RestaurantDB.getInstance(requireContext()).deliveryDao().findDeliveryByRestaurant(idRest)
        adapter.submitList(deliveries.toModel())
        Log.e("deletedelivery", "eliminado")
        Toast.makeText(context, "delivery eliminado", Toast.LENGTH_SHORT).show()
    }

    private fun deleteRestaurant() {
        RetrofitConfig.service
            .delRest(idRest)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
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
}