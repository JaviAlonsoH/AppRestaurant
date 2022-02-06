package com.example.apprestaurant.ui.restaurantUI.deliveryUI

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.apprestaurant.R
import com.example.apprestaurant.databinding.FragmentEditDeliveryBinding
import com.example.apprestaurant.databinding.FragmentEditRestaurantBinding
import com.example.apprestaurant.databinding.FragmentRestaurantDetailBinding
import com.example.apprestaurant.db.RestaurantDB
import com.example.apprestaurant.db.entities.DeliveryEntity
import com.example.apprestaurant.db.entities.RestaurantEntity
import com.example.apprestaurant.ui.restaurantUI.EditRestaurantFragmentDirections
import com.example.apprestaurant.ui.restaurantUI.RestaurantDetailFragment
import com.example.apprestaurant.ui.restaurantUI.RestaurantDetailFragmentDirections

class EditDeliveryFragment : Fragment() {

    private var _binding: FragmentEditDeliveryBinding? = null
    private val binding get() = _binding!!
    private val args: EditDeliveryFragmentArgs by navArgs()
    private var idDelivery: Int = 0
    private var idRestaurant: Int = 0
    private var delivery: DeliveryEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.let {
            idDelivery = it.idDelivery
            idRestaurant = it.restId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSaveDeliveryEdit.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Confirm changes?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    updateDelivery()
                    findNavController().popBackStack()
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

    private fun updateDelivery() {
        delivery = DeliveryEntity(
            binding.etDeliveryFood.text.toString(),
            idRestaurant,
            binding.etDeliveryAddress.text.toString(),
            idDelivery
        )
        RestaurantDB.getInstance(requireContext()).deliveryDao().updateDelivery(delivery!!)
    }
}