package com.example.crud.admin.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.crud.admin.model.PlaceDetails
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentAddPlaceBinding
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.showCustomToast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.random.Random


class AddPlace : BaseFragmentWithBinding<FragmentAddPlaceBinding>(
    FragmentAddPlaceBinding::inflate)
{

    private var divisionName : String = ""
    private var divisionNameBn : String = ""
    private lateinit var dbRef: DatabaseReference
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbRef = FirebaseDatabase.getInstance().reference

        init()
        initBn()

        binding.btnAddPlace.setOnClickListener{
            if (CheckNetwork(requireContext()).isNetworkConnected){
                if(validateInputFields() == "Ok"){
                    addPlaceToDB()
                }else{
                    Toast(requireContext()).showCustomToast(validateInputFields(),requireActivity())
                }
            }
        }
        binding.ivBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }



    private fun addPlaceToDB() {
        try {
            binding.progressBar.visibility = View.VISIBLE
            val random = Random.nextInt(1,2000)
            dbRef.child("places").child(binding.etPlaceDivision.text.toString())
                .child(binding.etPlaceName.text.toString()+random.toString())
                .setValue(PlaceDetails(
                    binding.etPlaceName.text.toString()+random.toString(),
                    binding.etPlaceName.text.toString(),
                    binding.etPlaceNameBn.text.toString(),
                    binding.etPlaceDistrict.text.toString(),
                    binding.etPlaceDistrictBn.text.toString(),
                    binding.etPlaceDivision.text.toString(),
                    binding.etPlaceDivisionBn.text.toString(),
                    binding.etPlaceImageLink.text.toString(),
                    binding.etPlaceLat.text.toString().toDouble(),
                    binding.etPlaceLong.text.toString().toDouble(),
                    binding.etPlaceDetails.text.toString(),
                    binding.etPlaceDetailsBn.text.toString()
                ))
            binding.etPlaceName.text = null
            binding.etPlaceNameBn.text = null
            binding.etPlaceDistrict.text = null
            binding.etPlaceDistrictBn.text = null
            binding.etPlaceImageLink.text = null
            binding.etPlaceLat.text = null
            binding.etPlaceLong.text = null
            binding.etPlaceDetails.text = null
            binding.etPlaceDetailsBn.text = null
            binding.progressBar.visibility = View.GONE
            Toast.makeText(requireContext(),"Place Added Successfully",Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            binding.progressBar.visibility = View.GONE
            Log.e("nlog",e.toString())
            Toast(requireContext()).showCustomToast("Something Went Wrong",requireActivity())
        }

    }

    private fun validateInputFields():String {
        if (binding.etPlaceName.text.toString().isEmpty()){
            return "Please enter place name"
        }else if (binding.etPlaceNameBn.text.toString().isEmpty()){
            return "Please enter place name in bengali"
        }else if (binding.etPlaceDistrict.text.toString().isEmpty()){
            return "Please enter place district"
        }else if (binding.etPlaceDistrictBn.text.toString().isEmpty()){
            return "Please enter place district in bengali"
        }else if (binding.etPlaceImageLink.text.toString().isEmpty()){
            return "Please enter image link"
        }else if (binding.etPlaceLat.text.isEmpty()){
            return "Please enter place latitude"
        }else if (binding.etPlaceLong.text.isEmpty()){
            return "Please enter place longitude"
        }else if (binding.etPlaceDetails.text.isEmpty()){
            return "Please enter place details"
        }else if (binding.etPlaceDetails.text.isEmpty()){
            return "Please enter place details in bengali"
        }else{
            return "Ok"
        }
    }

    private fun init() {
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
    private fun initBn() {
        //Bengali
        data class DivisionsBn(
            val namesBn: String
        ){
            override fun toString(): String {
                return namesBn
            }
        }

        val listBn = mutableListOf<DivisionsBn>()
        listBn.add(DivisionsBn("ঢাকা"))
        listBn.add(DivisionsBn("চট্টগ্রাম"))
        listBn.add(DivisionsBn("রাজশাহী"))
        listBn.add(DivisionsBn("খুলনা"))
        listBn.add(DivisionsBn("বরিশাল"))
        listBn.add(DivisionsBn("সিলেট"))
        listBn.add(DivisionsBn("ময়মনসিংহ"))
        listBn.add(DivisionsBn("রংপুর"))

        divisionNameBn = binding.etPlaceDivisionBn.text.toString()

        binding.etPlaceDivisionBn.setOnClickListener {
            binding.divisionListSpinnerBn.performClick()
        }

        val dataAdapterBn: ArrayAdapter<DivisionsBn> =
            ArrayAdapter<DivisionsBn>(
                requireContext(),
                com.chaos.view.R.layout.support_simple_spinner_dropdown_item,
                listBn
            )

        binding.divisionListSpinnerBn.adapter = dataAdapterBn

        binding.divisionListSpinnerBn.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.etPlaceDivisionBn.text = listBn[position].namesBn
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }
}