package com.example.crud.ui.divisions

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentPlacesListBinding
import com.example.crud.model.menu.PlaceListItem
import com.example.crud.ui.adapters.OnClickPlace
import com.example.crud.ui.adapters.PlaceListAdapter
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.showCustomToast


class PlacesListFragment : BaseFragmentWithBinding<FragmentPlacesListBinding>
    (FragmentPlacesListBinding::inflate),OnClickPlace{

    private val list: MutableList<PlaceListItem> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.ivBackBtn.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.recyclerViewDhakaDivision.setHasFixedSize(true)
        binding.recyclerViewDhakaDivision.layoutManager =
            GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)
        if(CheckNetwork(requireContext()).isNetworkConnected){
            //OK
        }else{
            Toast(requireContext()).showCustomToast(getString(R.string.pls_chk_internet),
                requireActivity())
        }
        when(requireArguments().getInt("divisionId")){
            1->{
                binding.txtPlaceBannerName.text = getString(R.string.dhaka_division)
                showDhakaDivision()
            }
            2->{
                binding.txtPlaceBannerName.text = getString(R.string.chittagong_division)
                showChittagongDivision()
            }
            3->{
                binding.txtPlaceBannerName.text = getString(R.string.khulna_division)
                showKhulnaDivision()
            }
            4->{
                binding.txtPlaceBannerName.text = getString(R.string.rajshahi_division)
            }
            5->{
                binding.txtPlaceBannerName.text = getString(R.string.barisal_divsion)
            }
            6->{
                binding.txtPlaceBannerName.text = getString(R.string.sylhet_division)
            }
            7->{
                binding.txtPlaceBannerName.text = getString(R.string.rangpur_division)
            }
            8->{
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

    private fun showKhulnaDivision() {
        list.add(PlaceListItem(
            1,
            "Sundarbans",
            "https://porzoton.com/wp-content/uploads/2020/04/Royal-Bengal-Tiger-Sundarban-Bangladesh.jpg"
            ,"2"))
        list.add(PlaceListItem(
            2,
            "Sixty Dome Mosque",
            "https://upload.wikimedia.org/wikipedia/commons/4/4f/Sixty_Dome_Mosque%2CBagerhat.jpg"
            ,"10"))
        list.add(PlaceListItem(
            3,
            "Hazrat Khan Jahan Ali Tomb",
            "https://upload.wikimedia.org/wikipedia/commons/3/38/Khan_jahan_ali_mazar_building.jpg"
            ,"10"))
        list.add(PlaceListItem(
            4,
            "Khodla Math Temple",
            "https://www.lrbtravelteam.com/wp-content/uploads/2020/11/Kodla-Moth.png"
            ,"5"))
        list.add(PlaceListItem(
            5,
            "Mausoleum of Lalon Shah",
            "https://upload.wikimedia.org/wikipedia/commons/8/8a/Fakir_lalon_saha_mazar.JPG"
            ,"8"))
        list.add(PlaceListItem(
            6,
            "Shilaidaha Rabindra Kuthibari",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Shilaidaha_Kuthibadi.jpg/1280px-Shilaidaha_Kuthibadi.jpg"
            ,"4"))
        list.add(PlaceListItem(
            7,
            "Nine Domed Mosque",
            "https://thumbs.dreamstime.com/b/nine-dome-mosque-bagarhat-bangladesh-centuries-old-today-ruin-attracts-local-tourists-114223822.jpg"
            ,"6"))
        showPlaceList(list)
    }

    private fun showChittagongDivision() {
        list.add(PlaceListItem(
            201,
            "Patenga Sea Beach",
            "https://tfe-bd.sgp1.cdn.digitaloceanspaces.com/uploads/1639194548.jpg"
            ,"17"))
        list.add(PlaceListItem(
            202,
            "Cox's Bazar Sea Beach",
            "https://ddnews.gov.in/sites/default/files/cox%20baazar.jpg"
            ,"20"))
        list.add(PlaceListItem(
            203,
            "Saint Martin Island",
            "https://pathfriend-bd.com/wp-content/uploads/2019/08/Coxs-Bazaar-Saintmartin.jpg"
            ,"10"))
        list.add(PlaceListItem(
            204,
            "Chandranath Hills",
            "https://www.observerbd.com/2018/05/23/1527094921.jpg"
            ,"17"))
        list.add(PlaceListItem(
            205,
            "Kaptai Lake",
            "https://www.localguidesconnect.com/t5/image/serverpage/image-id/1463475i85F976B454BD2D2A/image-size/large?v=v2&px=999"
            ,"17"))
        list.add(PlaceListItem(
            206,
            "Nafa-Khum Waterfall",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/63/NafaKhum%2CThanchi%2CBandarban.jpg/2560px-NafaKhum%2CThanchi%2CBandarban.jpg"
            ,"20"))
        list.add(PlaceListItem(
            207,
            "Buddha Dhatu Jadi",
            "https://live.staticflickr.com/5584/14854459840_e48bc3ee37_b.jpg"
            ,"10"))
        list.add(PlaceListItem(
            208,
            "Guliakhali Sea Beach",
            "https://www.localguidesconnect.com/t5/image/serverpage/image-id/698988i5EDBCE3C2BF4F1AA/image-size/large?v=v2&px=999"
            ,"17"))
        list.add(PlaceListItem(
            209,
            "Radiant Fish World",
            "https://lh3.googleusercontent.com/p/AF1QipM62wV9uk_itmunxyIkeloj3KHClxC9zol5IsTK=s680-w680-h510"
            ,"17"))
        showPlaceList(list)
    }

    private fun showDhakaDivision() {
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
        val bundle = Bundle()
        when(id){
            //cox bazaar
            202 ->{
                bundle.putInt("placeId",id)
                findNavController().navigate(R.id.action_placesListFragment_to_placeDetailsFragment,bundle)
            }else ->{
                Toast.makeText(requireContext(),getString(R.string.this_feature_is_under_development),Toast.LENGTH_SHORT).show()
            }
        }
    }

}