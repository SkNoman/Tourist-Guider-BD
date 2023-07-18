package com.example.crud.admin.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.crud.R
import com.example.crud.admin.model.PlaceDetails
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentAddPlaceBinding
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.showCustomToast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.chromium.support_lib_boundary.DropDataContentProviderBoundaryInterface


class AddPlace : BaseFragmentWithBinding<FragmentAddPlaceBinding>(
    FragmentAddPlaceBinding::inflate)
{

    var divisionName : String = ""
    private lateinit var dbRef: DatabaseReference
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbRef = FirebaseDatabase.getInstance().reference

        init()

        binding.btnAddPlace.setOnClickListener{
            if (CheckNetwork(requireContext()).isNetworkConnected){
                if(validateInputFields() == "Ok"){
                    addPlaceToDB()
                }else{
                    Toast(requireContext()).showCustomToast(validateInputFields(),requireActivity())
                }
            }
        }

    }

    private fun addPlaceToDB() {
        try {
            dbRef.child("places").child(binding.etPlaceDivision.text.toString())
                .child(binding.etPlaceName.text.toString())
                .setValue(PlaceDetails(
                    binding.etPlaceName.text.toString(),
                    binding.etPlaceDistrict.text.toString(),
                    binding.etPlaceDivision.text.toString(),
                    binding.etPlaceImageLink.text.toString(),
                    20.2121,
                    90.23232,
                    binding.etPlaceDetails.text.toString()
                ))
            Toast.makeText(requireContext(),"Place Added Successfully",Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            Log.e("nlog",e.toString())
            Toast(requireContext()).showCustomToast("Something Went Wrong",requireActivity())
        }

    }

    private fun validateInputFields():String {
        return "Ok"
    }

    private fun init()
    {
        data class Divisions(
            val names: String
        ){
            override fun toString(): String {
                return names
            }
        }

        val list = mutableListOf<Divisions>()
        list.add(Divisions("Dhaka"))
        list.add(Divisions("Chittagong"))
        list.add(Divisions("Rajshahi"))
        list.add(Divisions("Khulna"))
        list.add(Divisions("Barishal"))
        list.add(Divisions("Sylhet"))
        list.add(Divisions("Mymensing"))
        list.add(Divisions("Rangpur"))
        divisionName = binding.etPlaceDivision.text.toString()


        binding.etPlaceDivision.setOnClickListener {
            binding.divisionListSpinner.performClick()
        }

        val dataAdapter: ArrayAdapter<Divisions> =
            ArrayAdapter<Divisions>(
                requireContext(),
                com.chaos.view.R.layout.support_simple_spinner_dropdown_item,
                list
            )

        binding.divisionListSpinner.adapter = dataAdapter

        binding.divisionListSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.etPlaceDivision.text = list[position].names
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }
}