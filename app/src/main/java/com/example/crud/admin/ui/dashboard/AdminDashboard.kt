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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crud.R
import com.example.crud.admin.adapters.OnClickDelete
import com.example.crud.admin.adapters.OnClickEdit
import com.example.crud.admin.adapters.PlaceListAdapterAdmin
import com.example.crud.admin.model.PlaceDetails
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentAdminDashboardBinding
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.Loader
import com.example.crud.utils.showCustomToast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminDashboard : BaseFragmentWithBinding<FragmentAdminDashboardBinding>(
    FragmentAdminDashboardBinding::inflate),OnClickDelete,OnClickEdit
{
    private val pD: MutableList<PlaceDetails> = mutableListOf()
    private lateinit var dbRef: DatabaseReference
    var divisionName : String = ""

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

        val adminName = requireArguments().getString("admin_name")
        "Hello, $adminName".also { binding.txtAdminName.text = it }

        dbRef = FirebaseDatabase.getInstance().reference
        binding.placeListRecyclerViewAdmin.setHasFixedSize(true)
        binding.placeListRecyclerViewAdmin.layoutManager =
            GridLayoutManager(activity,1, GridLayoutManager.VERTICAL,false)

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
        divisionName = binding.txtFieldDropdown.text.toString()


        binding.txtFieldDropdown.setOnClickListener {
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
                    divisionName = list[position].names
                    showPlaces(divisionName)
                    binding.txtFieldDropdown.text = list[position].names
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        binding.btnLogout.setOnClickListener{
            findNavController().navigate(R.id.login)
        }

        binding.btnAddPlace.setOnClickListener{
            findNavController().navigate(R.id.addPlace)
        }
        //Always show dhaka division first
        showPlaces("Dhaka")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPlaces(division: String)
    {
        if (CheckNetwork(requireContext()).isNetworkConnected){
            showLoader(true)
            dbRef.child("places").child(division).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.e("nlog-enters","yes")
                    if (snapshot.exists()) {
                        binding.placeListRecyclerViewAdmin.visibility = View.VISIBLE
                        pD.clear()
                        for (placeSnapshot in snapshot.children) {
                            //val placeName = snapshot.key
                            val place = placeSnapshot.getValue(PlaceDetails::class.java)
                            place?.let {
                                pD.add(it)
                            }
                        }
                        showLoader(false)
                        showPlaceList(pD)
                    }else{
                        showLoader(false)
                        binding.placeListRecyclerViewAdmin.visibility = View.GONE
                        Toast.makeText(requireContext(),"No Place Found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle any errors that occur during the data retrieval
                    showLoader(false)
                    Toast.makeText(requireContext(),"Something Went Wrong\n" +
                            "$error", Toast.LENGTH_SHORT).show()
                }
            })
        }else{
            Toast(requireContext()).showCustomToast("Please turn on internet",requireActivity())
        }
        showLoader(false)
    }

    private fun showPlaceList(list: List<PlaceDetails>) {
        binding.placeListRecyclerViewAdmin.adapter =
            PlaceListAdapterAdmin(requireContext(), list,this,this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClickDelete(placeKey: String, division: String) {
        showLoader(true)
        dbRef.child("places").child(division).child(placeKey).removeValue()
            .addOnSuccessListener {
                // Deletion successful
                showLoader(false)
                Toast.makeText(requireContext(), "Place Deleted Successfully",Toast.LENGTH_SHORT).show()
                updateUI()
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occur during deletion
                showLoader(false)
                Log.e("Firebase", "Error deleting data: ${exception.message}")
                Toast.makeText(requireContext(), "Something went wrong",Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUI() {
        dbRef.child("places").child(divisionName).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    binding.placeListRecyclerViewAdmin.visibility = View.VISIBLE
                    pD.clear()
                    for (placeSnapshot in snapshot.children) {
                        val placeName = snapshot.key
                        Log.e("placeKey",placeName.toString())
                        val place = placeSnapshot.getValue(PlaceDetails::class.java)
                        place?.let {
                            pD.add(it)
                        }
                    }
                    Log.e("nlog",pD[0].detailsEn.toString())
                    showLoader(false)
                    showPlaceList(pD)


                }else{
                    binding.placeListRecyclerViewAdmin.visibility = View.GONE
                    showLoader(false)
                    Toast.makeText(requireContext(),"No Place Found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that occur during the data retrieval
                binding.placeListRecyclerViewAdmin.visibility = View.GONE
                showLoader(false)
                Log.e("Firebase", "Data retrieval cancelled: ${error.message}")
            }
        })
    }

    override fun onClickEdit(placeKey: String) {

        for (i in pD.indices){
            if (pD[i].placeKey == placeKey){
                val bundle = Bundle()
                bundle.putString("placeKey",pD[i].placeKey)
                bundle.putString("nameEn",pD[i].nameEn)
                bundle.putString("districtEn",pD[i].districtEn)
                bundle.putString("detailsEn",pD[i].detailsEn)
                bundle.putString("divisionEn",pD[i].divisionEn)

                //bn
                bundle.putString("nameBn",pD[i].nameBn)
                bundle.putString("districtBn",pD[i].districtBn)
                bundle.putString("detailsBn",pD[i].detailsBn)
                bundle.putString("divisionBn",pD[i].divisionBn)

                bundle.putString("image-link",pD[i].imageLink)
                pD[i].lat?.let { bundle.putDouble("lat", it) }
                pD[i].long?.let { bundle.putDouble("long",it) }
                findNavController().navigate(R.id.editPlace,bundle)
            }
        }
    }
}