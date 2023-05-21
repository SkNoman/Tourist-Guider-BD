package com.example.crud.ui.divisions

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentPlaceDetailsBinding
import com.example.crud.model.PlaceDetails
import com.example.crud.utils.GoogleMaps

class PlaceDetailsFragment : BaseFragmentWithBinding<FragmentPlaceDetailsBinding>
    (FragmentPlaceDetailsBinding::inflate){

    private val args by navArgs<PlaceDetailsFragmentArgs>()
    private lateinit var result: PlaceDetails
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //GET THE PLACE DETAILS FROM LIST PAGE.
        result = args.pD
        try {
            binding.apply {
                txtPlaceNameD.text = result.placeName
                txtDescValueD.text = result.placeDetails
                txtDistrictD.text = result.placeDistrict
                Glide.with(requireContext()).load(result.placeImage)
                    .placeholder(R.drawable.item3).into(ivPlaceImage)
                binding.txtMapDirection.setOnClickListener{
                    GoogleMaps.openGoogleMaps(requireContext(),result.lat,result.long)
                }
            }
        }catch (e:Exception){
            Log.e("error",e.toString())
        }



        binding.ivBackD.setOnClickListener{
            findNavController().popBackStack()
        }

    }
}