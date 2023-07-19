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


class PlacesListFragment : BaseFragmentWithBinding<FragmentPlacesListBinding>
    (FragmentPlacesListBinding::inflate),OnClickPlace{

    private val pD: MutableList<PlaceDetails> = mutableListOf()
    private lateinit var dbRef: DatabaseReference


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        dbRef = FirebaseDatabase.getInstance().reference

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
        when(requireArguments().getInt("divisionId")){
            1->{
                binding.txtPlaceBannerName.text = getString(R.string.dhaka_division)
                showPlaces("Dhaka")
            }
            2->{
                showPlaces("Chittagong")
                binding.txtPlaceBannerName.text = getString(R.string.chittagong_division)

            }
            3->{
                showPlaces("Khulna")
                binding.txtPlaceBannerName.text = getString(R.string.khulna_division)

            }
            4->{
                showPlaces("Rajshahi")
                binding.txtPlaceBannerName.text = getString(R.string.rajshahi_division)

            }
            5->{
                showPlaces("Barishal")
                binding.txtPlaceBannerName.text = getString(R.string.barisal_divsion)
            }
            6->{
                showPlaces("Sylhet")
                binding.txtPlaceBannerName.text = getString(R.string.sylhet_division)
            }
            7->{
                showPlaces("Rangpur")
                binding.txtPlaceBannerName.text = getString(R.string.rangpur_division)
            }
            8->{
                showPlaces("Mymensing")
                binding.txtPlaceBannerName.text = getString(R.string.mymensingh_division)
            }
        }

        binding.searchPlaces.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPlaces(division: String) {
        if (CheckNetwork(requireContext()).isNetworkConnected){
            dbRef.child("places").child(division).addListenerForSingleValueEvent(object :
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
                        showPlaceList(pD)
                    } else {
                        //binding.progressBarDB.visibility = View.GONE
                        Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
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


    private fun showPlaceList(list: List<PlaceDetails>) {
        binding.recyclerViewDhakaDivision.adapter =
            PlaceListAdapter(requireContext(), list,this)
        }

    fun filter(text: String?) {
        val temp: MutableList<PlaceDetails> = ArrayList()
       // temp.clear()
        for (s in pD) {
            if (s.name!!.contains(text!!,true)) {
                temp.add(s)
            }
        }

        binding.recyclerViewDhakaDivision.adapter =
            PlaceListAdapter(requireContext(), temp,this)
    }

    override fun onClick(name: String) {
       for (i in pD.indices){
            if (pD[i].name == name){
                val bundle = Bundle()
                bundle.putString("name",pD[i].name)
                bundle.putString("details",pD[i].details)
                bundle.putString("district",pD[i].district)
                bundle.putString("division",pD[i].division)
                bundle.putString("image-link",pD[i].imageLink)
                pD[i].lat?.let { bundle.putDouble("lat", it) }
                pD[i].long?.let { bundle.putDouble("long",it) }
                findNavController().navigate(R.id.placeDetailsFragment,bundle)
            }
       }
    }

}