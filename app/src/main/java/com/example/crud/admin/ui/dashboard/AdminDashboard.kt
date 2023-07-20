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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crud.R
import com.example.crud.admin.adapters.OnClickDelete
import com.example.crud.admin.adapters.OnClickEdit
import com.example.crud.admin.adapters.PlaceListAdapterAdmin
import com.example.crud.admin.model.PlaceDetails
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentAdminDashboardBinding
import com.example.crud.ui.adapters.PlaceListAdapter
import com.example.crud.utils.CheckNetwork
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
    private fun showPlaces(division: String) {
        if (CheckNetwork(requireContext()).isNetworkConnected){
            dbRef.child("places").child(division).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        binding.placeListRecyclerViewAdmin.visibility = View.VISIBLE
                        pD.clear()
                        for (placeSnapshot in snapshot.children) {
                             val placeName = snapshot.key
                            Log.e("place-name",placeName.toString())
                            val place = placeSnapshot.getValue(PlaceDetails::class.java)
                            place?.let {
                                pD.add(it)
                            }
                        }
                        Log.e("nlog",pD[0].detailsEn.toString())
                        showPlaceList(pD)
                        if(pD.isEmpty()){
                            Toast.makeText(requireContext(),"No Place Found", Toast.LENGTH_SHORT).show()
                        }

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
        binding.placeListRecyclerViewAdmin.adapter =
            PlaceListAdapterAdmin(requireContext(), list,this,this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClickDelete(name: String, division: String) {

        dbRef.child("places").child(division).child(name).removeValue()
            .addOnSuccessListener {
                // Deletion successful
                Toast.makeText(requireContext(), "$name Deleted Successfully",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occur during deletion
                Log.e("Firebase", "Error deleting data: ${exception.message}")
                Toast.makeText(requireContext(), "Something went wrong",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onClickEdit(name: String) {
        Toast.makeText(requireContext(),"Click Edit button for $name",Toast.LENGTH_SHORT).show()
    }
}