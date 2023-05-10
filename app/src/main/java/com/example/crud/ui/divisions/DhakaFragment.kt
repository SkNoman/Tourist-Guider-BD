package com.example.crud.ui.divisions

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentDhakaBinding
import com.example.crud.model.menu.PlaceListItem
import com.example.crud.ui.adapters.OnClickPlace
import com.example.crud.ui.adapters.PlaceListAdapter
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.showCustomToast


class DhakaFragment : BaseFragmentWithBinding<FragmentDhakaBinding>
    (FragmentDhakaBinding::inflate),OnClickPlace{

    private val list: MutableList<PlaceListItem> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerViewDhakaDivision.setHasFixedSize(true)
        binding.recyclerViewDhakaDivision.layoutManager =
            GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)
        if(CheckNetwork(requireContext()).isNetworkConnected){
            initializeList()
        }else{
            Toast(requireContext()).showCustomToast(getString(R.string.pls_chk_internet),
                requireActivity())
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

    private fun initializeList() {

        list.add(PlaceListItem(
            1,
            "Ahsan Manjil",
            "https://images.unsplash.com/photo-1564034503-e7c9edcb420c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
            ,"17"))
        list.add(PlaceListItem(
            2,
            "Lalbag Kella",
            "https://images.unsplash.com/photo-1564034503-e7c9edcb420c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
            ,"20"))
        list.add(PlaceListItem(
            3,
            "Hatirjheel",
            "https://images.unsplash.com/photo-1564034503-e7c9edcb420c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
            ,"10"))
        list.add(PlaceListItem(
            4,
            "Fantasy Kingdom",
            "https://images.unsplash.com/photo-1564034503-e7c9edcb420c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
            ,"17"))
        list.add(PlaceListItem(
            5,
            "National Museum",
            "https://images.unsplash.com/photo-1564034503-e7c9edcb420c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
            ,"17"))
        list.add(PlaceListItem(
            6,
            "Safari Park",
            "https://images.unsplash.com/photo-1564034503-e7c9edcb420c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
            ,"20"))
        list.add(PlaceListItem(
            7,
            "National Zoo",
            "https://images.unsplash.com/photo-1564034503-e7c9edcb420c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
            ,"10"))
        list.add(PlaceListItem(
            8,
            "Old Dhaka",
            "https://images.unsplash.com/photo-1564034503-e7c9edcb420c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
            ,"17"))
        showPlaceList(list)
    }

    private fun showPlaceList(list: List<PlaceListItem>) {
        binding.recyclerViewDhakaDivision.adapter =
            PlaceListAdapter(requireContext(), list,this)
        }

    fun filter(text: String?) {
        val temp: MutableList<PlaceListItem> = ArrayList()

        for (s in list) {
            if (s.placeName!!.contains(text!!,true)) {
                temp.add(s)
            }
        }

        binding.recyclerViewDhakaDivision.adapter =
            PlaceListAdapter(requireContext(), temp,this)
    }

    override fun onClick(id: Int) {
        Toast.makeText(requireContext(),getString(R.string.this_feature_is_under_development),Toast.LENGTH_SHORT).show()
    }

}