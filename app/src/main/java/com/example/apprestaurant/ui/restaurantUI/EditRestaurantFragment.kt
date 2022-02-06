package com.example.apprestaurant.ui.restaurantUI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.apprestaurant.R
import com.example.apprestaurant.databinding.FragmentEditRestaurantBinding
import com.example.apprestaurant.db.RestaurantDB
import com.example.apprestaurant.db.entities.RestaurantEntity

class EditRestaurantFragment : Fragment() {

    private var _binding: FragmentEditRestaurantBinding? = null
    private val binding get() = _binding!!
    private val args: EditRestaurantFragmentArgs by navArgs()
    private var restId: Int = 0
    private var name: String = ""
    private var rest: RestaurantEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.let {
            restId = it.restId
            name = it.name
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditRestaurantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSaveRestaurantEdit.setOnClickListener {
            updateRestaurant()
            val action = EditRestaurantFragmentDirections.editToDetail(
                binding.etRestaurantName.text.toString(),
                binding.etRestaurantFoodtype.text.toString(),
                binding.etRestaurantRating.text.toString(),
                restId
            )
            findNavController().navigate(action)
        }
    }

    private fun updateRestaurant() {
        rest = RestaurantEntity(
                binding.etRestaurantName.text.toString(),
                binding.etRestaurantFoodtype.text.toString(),
                binding.etRestaurantRating.text.toString(),
                restId
            )
        RestaurantDB.getInstance(requireContext()).restaurantDao().updateRestaurant(rest!!)
    }
}