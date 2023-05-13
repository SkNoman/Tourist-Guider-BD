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
import com.example.crud.model.PlaceDetails
import com.example.crud.model.menu.PlaceListItem
import com.example.crud.ui.adapters.OnClickPlace
import com.example.crud.ui.adapters.PlaceListAdapter
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.L
import com.example.crud.utils.PIL
import com.example.crud.utils.showCustomToast
import kotlin.math.PI


class PlacesListFragment : BaseFragmentWithBinding<FragmentPlacesListBinding>
    (FragmentPlacesListBinding::inflate),OnClickPlace{

    private val list: MutableList<PlaceListItem> = mutableListOf()
    private val pD: MutableList<PlaceDetails> = mutableListOf()


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
        list.clear()
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
        list.clear()
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
/*        list.add(PlaceListItem(
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
            ,"17"))*/
        showPlaceList(list)
    }

    private fun showDhakaDivision() {
        list.clear()
        list.add(PlaceListItem(
            101,
            getString(R.string.place_ahsan_manzil),
            PIL.PLACE_AHSAN_MANZIL_IMAGE
            ,"17"))
        list.add(PlaceListItem(
            102,
             getString(R.string.place_lalbagh_fort)
             ,PIL.PLACE_LALBAGH_FORT_IMAGE
            ,"20"))
        list.add(PlaceListItem(
            103,
             getString(R.string.place_national_parliament)
            ,PIL.PLACE_NATIONAL_PARLIAMENT_IMAGE
            ,"10"))
        list.add(PlaceListItem(
            104,
             getString(R.string.place_sonargaon_museum)
           ,PIL.PLACE_SONARGAON_MUSEUM_IMAGE
            ,"17"))
        list.add(PlaceListItem(
            105,
            getString(R.string.place_bangabandhu_safari_park),
            PIL.PLACE_BANGABANDHU_SAFARI_PARK_IMAGE
            ,"17"))
        list.add(PlaceListItem(
            106,
             getString(R.string.place_bangabandhu_military_museum)
            ,PIL.PLACE_BANGABANDHU_MILITARY_MUSEUM_IMAGE
            ,"20"))
        list.add(PlaceListItem(
            107,
            getString(R.string.place_taj_mahal_bangladesh)
            ,PIL.PLACE_TAJ_MAHAL_BANGLADESH_IMAGE
            ,"10"))
        list.add(PlaceListItem(
            108,
            getString(R.string.place_hatir_jheel)
           ,PIL.PLACE_HATIR_JHEEL_IMAGE
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
        when(id){

            //100 - > DHAKA
            101 -> {
                pD.add(PlaceDetails(getString(R.string.place_ahsan_manzil),
                    getString(R.string.place_ahsan_manzil_details),
                    getString(R.string.dhaka), L.LAT_AHSAN_MANZIL_MUSEUM,
                    L.LONG_AHSAN_MANZIL_MUSEUM, PIL.PLACE_AHSAN_MANZIL_IMAGE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            102 -> {
                pD.add(PlaceDetails(getString(R.string.place_lalbagh_fort),
                    getString(R.string.place_lalbagh_fort_details),
                    getString(R.string.dhaka), L.LAT_LALBAGH_FORT,
                    L.LONG_LALBAGH_FORT, PIL.PLACE_LALBAGH_FORT_IMAGE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            103 -> {
                pD.add(PlaceDetails(getString(R.string.place_national_parliament),
                    getString(R.string.place_national_parliament_details),
                    getString(R.string.dhaka), L.LAT_BANGLADESH_NATIONAL_PARLIAMENT,
                    L.LONG_BANGLADESH_NATIONAL_PARLIAMENT, PIL.PLACE_NATIONAL_PARLIAMENT_IMAGE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            104 -> {
                pD.add(PlaceDetails(getString(R.string.place_sonargaon_museum),
                    getString(R.string.place_sonargaon_museum_details),
                    getString(R.string.dhaka), L.LAT_SONARGAON_MUSEUM,
                    L.LONG_SONARGAON_MUSEUM, PIL.PLACE_SONARGAON_MUSEUM_IMAGE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            105 -> {
                pD.add(PlaceDetails(getString(R.string.place_bangabandhu_safari_park),
                    getString(R.string.place_bangabandhu_safari_park_details),
                    getString(R.string.dhaka), L.LAT_BANGABANDHU_SAFARI_PARK,
                    L.LONG_BANGABANDHU_SAFARI_PARK, PIL.PLACE_BANGABANDHU_SAFARI_PARK_IMAGE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            106 -> {
                pD.add(PlaceDetails(getString(R.string.place_bangabandhu_military_museum),
                    getString(R.string.place_bangabandhu_military_museum_details),
                    getString(R.string.dhaka), L.LAT_BANGABANDHU_MILITARY_MUSEUM,
                    L.LONG_BANGABANDHU_MILITARY_MUSEUM, PIL.PLACE_BANGABANDHU_MILITARY_MUSEUM_IMAGE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            107 -> {
                pD.add(PlaceDetails(getString(R.string.place_taj_mahal_bangladesh),
                    getString(R.string.place_taj_mahal_bangladesh_details),
                    getString(R.string.dhaka), L.LAT_BANGABANDHU_MILITARY_MUSEUM,
                    L.LONG_BANGABANDHU_MILITARY_MUSEUM, PIL.PLACE_TAJ_MAHAL_BANGLADESH_IMAGE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            108 ->{
                pD.add(PlaceDetails(getString(R.string.place_hatir_jheel),
                    getString(R.string.place_hatir_jheel_details),
                    getString(R.string.dhaka), L.LAT_HATIR_JHEEL,
                    L.LONG_HATIR_JHEEL, PIL.PLACE_HATIR_JHEEL_IMAGE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }







            //SERIES 200 IS FOR CHITTAGONG DIVISION
            201 -> {
                pD.add(PlaceDetails(getString(R.string.place_patenga_sea_beach)
                ,getString(R.string.place_patenga_sea_beach_description),
                getString(R.string.chittagong), L.LAT_PATENGA_SEA_BEACH,
                L.LONG_PATENGA_SEA_BEACH,
                PIL.PATENGA_SEA_BEACH))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            //cox bazaar
            202 ->{
                pD.add(PlaceDetails(getString(R.string.cox_s_bazar_sea_beach)
                    ,getString(R.string.cox_bazar_desc),
                    getString(R.string.chittagong),
                    L.LAT_COX_BAZAR_SEA_BEACH,
                    L.LONG_COX_BAZAR_SEA_BEACH,
                    PIL.COXS_BAZAR_SEA_BEACH))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }else ->{
                Toast.makeText(requireContext(),getString(R.string.this_feature_is_under_development),Toast.LENGTH_SHORT).show()
            }
        }
    }

}