package com.example.crud.admin.ui.dashboard

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentAdminDashboardBinding

class AdminDashboard : BaseFragmentWithBinding<FragmentAdminDashboardBinding>(
    FragmentAdminDashboardBinding::inflate)
{
    var divisionName : String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adminName = requireArguments().getString("admin_name")
        "Hello, $adminName".also { binding.txtAdminName.text = it }

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
    }
}