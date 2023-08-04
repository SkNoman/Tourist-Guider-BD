package com.example.crud.admin.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentEditPlaceBinding
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.Loader
import com.example.crud.utils.showCustomToast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class EditPlace : BaseFragmentWithBinding<FragmentEditPlaceBinding>(
    FragmentEditPlaceBinding::inflate)
{
    private var placeKey : String = ""
    private var division: String = ""
    private var divisionName : String = ""
    private var divisionNameBn : String = ""
    private lateinit var dbRef: DatabaseReference

    private lateinit var dialog: DialogFragment

    private fun showLoader(show: Boolean){

        if (show){
            dialog = Loader()
            dialog.show(childFragmentManager, "Loader")
            dialog.isCancelable = false
        }else{
            dialog.dismiss()
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbRef = FirebaseDatabase.getInstance().reference

        getDataAndSet()
        init()
        initBn()

        binding.btnUpdatePlace.setOnClickListener{
            if (CheckNetwork(requireContext()).isNetworkConnected){
                if(validateInputFields() == "Ok"){
                    updatePlace()
                }else{
                    Toast(requireContext()).showCustomToast(validateInputFields(),requireActivity())
                }
            }
        }
        binding.ivBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun getDataAndSet()
    {
        try {
            binding.apply {
                placeKey = requireArguments().getString("placeKey", "Place Key")
                division = requireArguments().getString("divisionEn", "Place Division")

                etPlaceName.setText(requireArguments().getString("nameEn", "Place Name"))
                etPlaceNameBn.setText(requireArguments().getString("nameBn", "Place Name"))

                etPlaceDistrict.setText(requireArguments().getString("districtEn", "Place Name"))
                etPlaceDistrictBn.setText(requireArguments().getString("districtBn", "Place Name"))

                etPlaceImageLink.setText(requireArguments().getString("image-link", "Place Image"))

                etPlaceLat.setText(requireArguments().getDouble("lat", 0.0).toString())
                etPlaceLong.setText(requireArguments().getDouble("long", 0.0).toString())

                etPlaceDetails.setText(requireArguments().getString("detailsEn","Place Details"))
                etPlaceDetailsBn.setText(requireArguments().getString("detailsBn","Place Details"))
            }
        }catch (e:Exception){
            Log.e("nlog-e",e.toString())
        }
    }



    private fun updatePlace()
    {
        try {
            showLoader(true)

            val placeData  = hashMapOf<String, Any>(
                "nameEn" to binding.etPlaceName.text.toString(),
                "nameBn" to binding.etPlaceNameBn.text.toString(),
                "districtEn" to binding.etPlaceDistrict.text.toString(),
                "districtBn" to binding.etPlaceDistrictBn.text.toString(),
                /*"divisionEn" to binding.etPlaceDivision.text.toString(),
                "divisionBn" to binding.etPlaceDivisionBn.text.toString(),*/
                "imageLink" to binding.etPlaceImageLink.text.toString(),
                "latitude" to binding.etPlaceLat.text.toString().toDouble(),
                "longitude" to binding.etPlaceLong.text.toString().toDouble(),
                "detailsEn" to binding.etPlaceDetails.text.toString(),
                "detailsBn" to binding.etPlaceDetailsBn.text.toString()
            )

            Log.e("placeKey", placeKey)
            dbRef.child("places").child(division)
                .child(placeKey).updateChildren(placeData)
                .addOnSuccessListener {
                    binding.etPlaceName.text = null
                    binding.etPlaceNameBn.text = null
                    binding.etPlaceDistrict.text = null
                    binding.etPlaceDistrictBn.text = null
                    binding.etPlaceImageLink.text = null
                    binding.etPlaceLat.text = null
                    binding.etPlaceLong.text = null
                    binding.etPlaceDetails.text = null
                    binding.etPlaceDetailsBn.text = null
                    showLoader(false)
                    findNavController().popBackStack()
                    Toast.makeText(requireContext(),"Place Updated Successfully",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    showLoader(false)
                    Toast(requireContext()).showCustomToast("Something Went Wrong",requireActivity())
                }
        }catch (e:Exception){
                showLoader(false)
            Log.e("nlog-e",e.toString())
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