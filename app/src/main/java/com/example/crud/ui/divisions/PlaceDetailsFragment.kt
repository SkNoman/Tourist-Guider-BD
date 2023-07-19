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
import com.example.crud.utils.GoogleMaps

class PlaceDetailsFragment : BaseFragmentWithBinding<FragmentPlaceDetailsBinding>
    (FragmentPlaceDetailsBinding::inflate){


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        try {
           binding.apply {
                txtPlaceNameD.text = requireArguments().getString("name","Place Name")
                txtDescValueD.text = requireArguments().getString("details","Place Details")
                txtDistrictD.text = requireArguments().getString("district","Place District")
                Glide.with(requireContext()).load(requireArguments().getString("image-link","https://media.istockphoto.com/id/516449022/photo/lady-with-kayak.jpg?s=612x612&w=0&k=20&c=Yp-rzpmY_hbhpbTE38z6toouRKW-lAEN-ZvuWvH8kKE="))
                    .placeholder(R.drawable.item3).into(ivPlaceImage)
                binding.txtMapDirection.setOnClickListener{
                    GoogleMaps.openGoogleMaps(requireContext(),
                        requireArguments().getDouble("lat",0.0),
                        requireArguments().getDouble("long",0.0),
                        requireArguments().getString("name","Place name"),
                        requireArguments().getString("district","Bangladesh"))
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