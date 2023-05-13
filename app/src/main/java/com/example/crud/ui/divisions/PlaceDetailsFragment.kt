package com.example.crud.ui.divisions

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentPlaceDetailsBinding
import com.example.crud.utils.GoogleMaps

class PlaceDetailsFragment : BaseFragmentWithBinding<FragmentPlaceDetailsBinding>
    (FragmentPlaceDetailsBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivBackD.setOnClickListener{
            findNavController().popBackStack()
        }
        when(requireArguments().getInt("placeId")){
            202 -> {
                Glide.with(requireContext()).load("https://ddnews.gov.in/sites/default/files/cox%20baazar.jpg")
                    .placeholder(R.drawable.preloader).into(binding.ivPlaceImage)
                binding.txtPlaceNameD.text = getString(R.string.cox_s_bazar_sea_beach)
                binding.txtDescValueD.text = getString(R.string.cox_bazar_desc)
                binding.txtDistrictD.text = getString(R.string.cox_s_bazar)
                binding.txtMapDirection.setOnClickListener{
                    GoogleMaps.openGoogleMaps(requireContext(),21.4285,91.9702)
                }
            }
        }
    }
}