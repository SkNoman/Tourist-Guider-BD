package com.example.crud.ui.divisions

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crud.R
import com.example.crud.admin.model.PlaceDetails
import com.example.crud.admin.model.PlaceName
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentPlacesListBinding
import com.example.crud.ui.adapters.OnClickPlace
import com.example.crud.ui.adapters.PlaceListAdapter
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.L
import com.example.crud.utils.PIL
import com.example.crud.utils.showCustomToast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.ln


class PlacesListFragment : BaseFragmentWithBinding<FragmentPlacesListBinding>
    (FragmentPlacesListBinding::inflate),OnClickPlace{

    private val pD: MutableList<PlaceDetails> = mutableListOf()
    private lateinit var dbRef: DatabaseReference
    private var lnType = ""


    @RequiresApi(Build.VERSION_CODES.M)
    private fun initialize(languageType: String) {
        when(requireArguments().getInt("divisionId")){
            1->{
                binding.txtPlaceBannerName.text = getString(R.string.dhaka_division)
                showPlaces("Dhaka",languageType)
            }
            2->{
                showPlaces("Chittagong",languageType)
                binding.txtPlaceBannerName.text = getString(R.string.chittagong_division)

            }
            3->{
                showPlaces("Khulna",languageType)
                binding.txtPlaceBannerName.text = getString(R.string.khulna_division)

            }
            4->{
                showPlaces("Rajshahi",languageType)
                binding.txtPlaceBannerName.text = getString(R.string.rajshahi_division)

            }
            5->{
                showPlaces("Barishal",languageType)
                binding.txtPlaceBannerName.text = getString(R.string.barisal_divsion)
            }
            6->{
                showPlaces("Sylhet",languageType)
                binding.txtPlaceBannerName.text = getString(R.string.sylhet_division)
            }
            7->{
                showPlaces("Rangpur",languageType)
                binding.txtPlaceBannerName.text = getString(R.string.rangpur_division)
            }
            8->{
                showPlaces("Mymensing",languageType)
                binding.txtPlaceBannerName.text = getString(R.string.mymensingh_division)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        dbRef = FirebaseDatabase.getInstance().reference


        binding.switchLanguageWeb.setOnClickListener{
            if (binding.switchLanguageWeb.text == "Bangla") {
                lnType = "en"
                binding.switchLanguageWeb.text = "English"
                initialize("en")
            }else{
                lnType = "bn"
                binding.switchLanguageWeb.text = "Bangla"
                initialize("bn")
            }
        }

        initialize("en")

        binding.ivBackBtn.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.recyclerViewDhakaDivision.setHasFixedSize(true)
        binding.recyclerViewDhakaDivision.layoutManager =
            GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)
        if(!CheckNetwork(requireContext()).isNetworkConnected){
            Toast(requireContext()).showCustomToast(getString(R.string.pls_chk_internet),
                requireActivity())
        }


        binding.searchPlaces.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString(),lnType)
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPlaces(division: String,languageType: String) {
        if (CheckNetwork(requireContext()).isNetworkConnected){
            dbRef.child("places").child(division).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        pD.clear()
                        for (placeSnapshot in snapshot.children) {
                           // val placeName = snapshot.key
                           // Log.e("place-name",placeName.toString())
                            val place = placeSnapshot.getValue(PlaceDetails::class.java)
                            place?.let {
                                pD.add(it)
                            }
                        }
                        showPlaceList(pD,languageType)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle any errors that occur during the data retrieval
                    Log.e("Firebase", "Data retrieval cancelled: ${error.message}")
                }
            })
        }else{
            Toast(requireContext()).showCustomToast("Please turn on internet",requireActivity())
        }
    }


    private fun showPlaceList(list: List<PlaceDetails>,languageType: String) {
        binding.recyclerViewDhakaDivision.adapter =
            PlaceListAdapter(requireContext(), list,this,languageType)
        }

    fun filter(text: String?,lnType: String) {
        val temp: MutableList<PlaceDetails> = ArrayList()
       // temp.clear()
        for (s in pD) {
            if (s.nameEn!!.contains(text!!,true)
                || s.nameBn!!.contains(text,true)) {
                temp.add(s)
            }
        }

        binding.recyclerViewDhakaDivision.adapter =
            PlaceListAdapter(requireContext(), temp,this,lnType)
    }

    override fun onClick(name: String) {
       for (i in pD.indices){
            if (pD[i].nameBn == name){
                val bundle = Bundle()
                bundle.putString("name",pD[i].nameEn)
                bundle.putString("details",pD[i].detailsEn)
                bundle.putString("district",pD[i].districtBn)
                bundle.putString("division",pD[i].divisionBn)
                bundle.putString("image-link",pD[i].imageLink)
                pD[i].lat?.let { bundle.putDouble("lat", it) }
                pD[i].long?.let { bundle.putDouble("long",it) }
                findNavController().navigate(R.id.placeDetailsFragment,bundle)
            }
       }
    }

}