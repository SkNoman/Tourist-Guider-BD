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
        if(!CheckNetwork(requireContext()).isNetworkConnected){
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
                showRajshahiDivision()
            }
            5->{
                showBarishalDivision()
                binding.txtPlaceBannerName.text = getString(R.string.barisal_divsion)
            }
            6->{
                showSylhetDivision()
                binding.txtPlaceBannerName.text = getString(R.string.sylhet_division)
            }
            7->{
                showRangpurDivision()
                binding.txtPlaceBannerName.text = getString(R.string.rangpur_division)
            }
            8->{
                showMymensingDivision()
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

    private fun showMymensingDivision() {
        list.clear()
        list.add(
            PlaceListItem(
            801,
            getString(R.string.title_madhutila_eco_park),
            PIL.IMAGE_URL_MADHUTILA_ECO_PARK,
                "4"
        )
        )

        list.add(
            PlaceListItem(
            802,
            getString(R.string.title_china_matir_pahar),
            PIL.IMAGE_URL_CHINA_MATIR_PAHAR,
                "5"
        )
        )
        list.add(
            PlaceListItem(
                803,
                getString(R.string.place_name_shashi_lodge),
                PIL.IMAGE_URL_SHASHI_LODGE,
                "5"
            )
        )

        list.add(
            PlaceListItem(
                804,
                getString(R.string.place_name_luis_village_resort),
                PIL.IMAGE_URL_LUIS_VILLAGE_RESORT,
                "5"
            )
        )
        list.add(
            PlaceListItem(
                805,
                getString(R.string.place_name_muktagacha_zamindar_bari),
                PIL.IMAGE_URL_MUKTAGACHA_ZAMINDAR_BARI,
                "5"
            )
        )
                    showPlaceList(list)
    }

    private fun showRangpurDivision() {
        list.clear()
        list.add(
            PlaceListItem(
            701,
            getString(R.string.place_tajhat_palace),
            PIL.IMAGE_TAJHAT_PALACE,
            "2"
            )
        )
        list.add(PlaceListItem(
            702,
            getString(R.string.place_kantajew_temple),
            PIL.IMAGE_KANTAJEW_TEMPLE,
            "3"
        ))
        list.add(PlaceListItem(
            703,
            getString(R.string.place_shopnopuri_amusement_park),
            PIL.IMAGE_SHOPNOPURI_AMUSEMENT_PARK,
            "3"
        ))
        list.add(PlaceListItem(
            704,
            getString(R.string.place_name_rangpur_town_hall),
            PIL.IMAGE_RANGPUR_TOWN_HALL,
            "3"
        ))

        list.add(PlaceListItem(
            705,
            getString(R.string.place_name_rangpur_zoo),
            PIL.IAMGE_RANGPUR_ZOO,
            "3"
        ))
        list.add(PlaceListItem(
            706,
            getString(R.string.palce_name_nayabad_mosque),
            PIL.IMAGE_NAYABAD_MOSQUE,
            "3"
        ))
        showPlaceList(list)
    }

    private fun showSylhetDivision() {
        list.clear()
        list.add(PlaceListItem(
            601,
            getString(R.string.place_title_hazrat_shahjalal_mazar),
            PIL.IMAGE_HAZRAT_SHAHJALAL_MAZAR
            ,"20"))

        list.add(PlaceListItem(
            602,
            getString(R.string.place_title_sada_pathor),
            PIL.IMAGE_SADA_PATHOR
            ,"2"))
        list.add(PlaceListItem(
            603,
            getString(R.string.place_title_jaflong),
            PIL.IMAGE_JAFLONG
            ,"10"))

        list.add(PlaceListItem(
            604,
            getString(R.string.place_title_lalakhal),
            PIL.IMAGE_LALAKHAL
            ,"2"))
        list.add(PlaceListItem(
            605,
            getString(R.string.place_title_ratargul_swamp_forest),
            PIL.IMAGE_RATARGUL_SWAMP_FOREST
            ,"2"))
        list.add(PlaceListItem(
            606,
            getString(R.string.place_title_malnichhera_tea_garden),
            PIL.IMAGE_MALNICHHERA_TEA_GARDEN
            ,"5"))
        list.add(PlaceListItem(
            607,
            getString(R.string.place_title_tanguar_haor),
            PIL.IMAGE_TANGUAR_HAOR
            ,"7"))
        showPlaceList(list)
    }

    private fun showBarishalDivision() {
        list.clear()
        list.add(PlaceListItem(
            501,
            getString(R.string.place_baitul_aman_jame_masjid),
            PIL.IMAGE_BAITUL_AMAN_JAME_MASJID
            ,"1"))
        list.add(PlaceListItem(
            502,
            getString(R.string.place_kuakata_sea_beach),
            PIL.IMAGE_KUAKATA_SEA_BEACH
            ,"20"))
        list.add(PlaceListItem(
            503,
            getString(R.string.place_floating_guava_market),
            PIL.IMAGE_FLOATING_GUAVA_MARKET
            ,"3"))
        list.add(PlaceListItem(
            504,
            getString(R.string.place_jakob_tower),
            PIL.IMAGE_JAKOB_TOWER
            ,"9"))
        list.add(PlaceListItem(
            505,
            getString(R.string.place_name_30_godown_monument),
            PIL.IMAGE_30_GODOWN_MONUMENT
            ,"9"))
        list.add(PlaceListItem(
            506,
            getString(R.string.place_name_bibir_pukur),
            PIL.IMAGE_BIBIR_PUKUR
            ,"9"))
        list.add(PlaceListItem(
            507,
            getString(R.string.place_name_freedom_fighters_park),
            PIL.IMAGE_FREEDOM_FIGHTERS_PARK
            ,"9"))

        showPlaceList(list)
    }

    private fun showRajshahiDivision() {
        list.clear()
        list.add(PlaceListItem(
            401,
            getString(R.string.place_name_puthia_temple_complex),
            PIL.IMAGE_LINK_PUTHIA_TEMPLE_COMPLEX
            ,"3"))
        list.add(PlaceListItem(
            402,
            getString(R.string.place_name_ruins_buddhist_vihara),
            PIL.IMAGE_LINK_RUINS_BUDDHIST_VIHARA
            ,"4"))
        list.add(PlaceListItem(
            403,
            getString(R.string.place_name_mohasthan_garh),
            PIL.IMAGE_LINK_MOHASTHAN_GARH
            ,"5"))
        list.add(PlaceListItem(
            404,
            getString(R.string.place_name_choto_shona_mosque),
            PIL.IMAGE_LINK_CHOTO_SHONA_MOSQUE
            ,"2"))

        list.add(PlaceListItem(
            405,
            getString(R.string.place_name_varendra_research_museum),
            PIL.IMAGE_URL_VARENDA_RESEARCH_MUSEUM
            ,"2"))

        list.add(PlaceListItem(
            406,
            getString(R.string.place_name_shahid_zia_shishu_park),
            PIL.IMAGE_URL_SHAHID_ZIA_SHISHU_PARK
            ,"2"))

        showPlaceList(list)
    }

    private fun showKhulnaDivision() {
        list.clear()
        list.add(PlaceListItem(
            301,
            getString(R.string.sundarbans),
            "https://porzoton.com/wp-content/uploads/2020/04/Royal-Bengal-Tiger-Sundarban-Bangladesh.jpg"
            ,"2"))
        list.add(PlaceListItem(
            302,
            getString(R.string.sixty_dome_mosque),
            "https://upload.wikimedia.org/wikipedia/commons/4/4f/Sixty_Dome_Mosque%2CBagerhat.jpg"
            ,"10"))
        list.add(PlaceListItem(
            303,
            getString(R.string.hazrat_khan_jahan_ali_tomb),
            "https://upload.wikimedia.org/wikipedia/commons/3/38/Khan_jahan_ali_mazar_building.jpg"
            ,"10"))
        list.add(PlaceListItem(
            304,
            getString(R.string.khodla_math_temple),
            "https://www.lrbtravelteam.com/wp-content/uploads/2020/11/Kodla-Moth.png"
            ,"5"))
        list.add(PlaceListItem(
            305,
            getString(R.string.mausoleum_of_lalon_shah),
            "https://upload.wikimedia.org/wikipedia/commons/8/8a/Fakir_lalon_saha_mazar.JPG"
            ,"8"))
        list.add(PlaceListItem(
            306,
            getString(R.string.shilaidaha_rabindra_kuthibari),
            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Shilaidaha_Kuthibadi.jpg/1280px-Shilaidaha_Kuthibadi.jpg"
            ,"4"))
        showPlaceList(list)
    }

    private fun showChittagongDivision() {
        list.clear()
        list.add(PlaceListItem(
            201,
            getString(R.string.place_patenga_sea_beach),
            "https://tfe-bd.sgp1.cdn.digitaloceanspaces.com/uploads/1639194548.jpg"
            ,"17"))
        list.add(PlaceListItem(
            202,
            getString(R.string.cox_s_bazar_sea_beach),
            "https://ddnews.gov.in/sites/default/files/cox%20baazar.jpg"
            ,"20"))
        list.add(PlaceListItem(
            203,
            getString(R.string.place_saint_martin),
            "https://pathfriend-bd.com/wp-content/uploads/2019/08/Coxs-Bazaar-Saintmartin.jpg"
            ,"10"))
        list.add(PlaceListItem(
            204,
            getString(R.string.place_chandranath_pahar),
            "https://www.observerbd.com/2018/05/23/1527094921.jpg"
            ,"17"))
        list.add(PlaceListItem(
            205,
            getString(R.string.place_kaptai_lake),
            "https://www.localguidesconnect.com/t5/image/serverpage/image-id/1463475i85F976B454BD2D2A/image-size/large?v=v2&px=999"
            ,"17"))
        list.add(PlaceListItem(
            206,
            getString(R.string.nafa_khum_waterfall),
            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/63/NafaKhum%2CThanchi%2CBandarban.jpg/2560px-NafaKhum%2CThanchi%2CBandarban.jpg"
            ,"20"))
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
                Log.e("nlog-lat-place","Lat: ${L.LAT_BANGLADESH_NATIONAL_PARLIAMENT}, Long: ${L.LONG_BANGLADESH_NATIONAL_PARLIAMENT}")
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



            //200 -> CHITTAGONG
            201 ->{
                pD.add(PlaceDetails(getString(R.string.place_patenga_sea_beach)
                ,getString(R.string.place_patenga_sea_beach_description),
                getString(R.string.chittagong), L.LAT_PATENGA_SEA_BEACH,
                L.LONG_PATENGA_SEA_BEACH,
                PIL.PATENGA_SEA_BEACH))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            202 ->{
                pD.add(PlaceDetails(getString(R.string.cox_s_bazar_sea_beach)
                    ,getString(R.string.cox_bazar_desc),
                    getString(R.string.cox_bazar),
                    L.LAT_COX_BAZAR_SEA_BEACH,
                    L.LONG_COX_BAZAR_SEA_BEACH,
                    PIL.COXS_BAZAR_SEA_BEACH))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            203 ->{
                pD.add(PlaceDetails(getString(R.string.place_saint_martin)
                    ,getString(R.string.place_saint_martin_description),
                    getString(R.string.cox_bazar),
                    L.LAT_SAINT_MARTIN_ISLAND,
                    L.LONG_SAINT_MARTIN_ISLAND,
                    PIL.SAINT_MARTIN_ISLAND))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            204 ->{
                Log.e("nlog-lat-place","Lat: ${L.LAT_CHANDRANATH_HILLS}, Long: ${L.LONG_CHANDRANATH_HILLS}")
                pD.add(PlaceDetails(getString(R.string.place_chandranath_pahar)
                    ,getString(R.string.place_chandranath_pahar_description),
                    getString(R.string.sitakund),
                    L.LAT_CHANDRANATH_HILLS,
                    L.LONG_CHANDRANATH_HILLS,
                    PIL.CHANDRANATH_HILLS))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            205 ->{
                pD.add(PlaceDetails(getString(R.string.place_kaptai_lake)
                    ,getString(R.string.place_kaptai_lake_description),
                    getString(R.string.rangamati),
                    L.LAT_KAPTAI_LAKE,
                    L.LONG_KAPTAI_LAKE,
                    PIL.KAPTAI_LAKE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            206 ->{
                pD.add(PlaceDetails(getString(R.string.nafa_khum_waterfall)
                    ,getString(R.string.nafa_khum_desc),
                    getString(R.string.rangamati),
                    L.LAT_NAFA_KHUM,
                    L.LONG_NAFA_KHUM,
                    PIL.NAFA_KHUM_WATERFALL))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            //300 -> KHULNA


            301 ->{
                pD.add(PlaceDetails(getString(R.string.sundarbans)
                    ,getString(R.string.shundarban_description),
                    getString(R.string.satkhira),
                    L.LAT_SHUNDARBAN,
                    L.LONG_SHUNDARBAN,
                    PIL.SHUNDARBAN))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            302 ->{
                pD.add(PlaceDetails(getString(R.string.sixty_dome_mosque)
                    ,getString(R.string.sixty_domed_mosque_description),
                    getString(R.string.khulna),
                    L.LAT_SIXTY_DOMED_MOSQUE,
                    L.LONG_SAINT_MARTIN_ISLAND,
                    PIL.SIXTY_DOMED_MOSQUE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            303 ->{
                pD.add(PlaceDetails(getString(R.string.hazrat_khan_jahan_ali_tomb)
                    ,getString(R.string.khan_jahan_ali_description),
                    getString(R.string.khulna),
                    L.LAT_KHAN_JAHAN_ALI,
                    L.LONG_KHAN_JAHAN_ALI,
                    PIL.KHAN_JAHAN_ALI))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            304 ->{
                pD.add(PlaceDetails(getString(R.string.khodla_math_temple)
                    ,getString(R.string.khondla_math_temple_description),
                    getString(R.string.khulna),
                    L.LAT_KHONDLA_MATH_TEMPLE,
                    L.LONG_KHONDLA_MATH_TEMPLE,
                    PIL.KHONDLA_MATH_TEMPLE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            305 ->{
                pD.add(PlaceDetails(getString(R.string.mausoleum_of_lalon_shah)
                    ,getString(R.string.lalon_shah_description),
                    getString(R.string.kushtia),
                    L.LAT_LALON_SHAH,
                    L.LONG_LALON_SHAH,
                    PIL.LALON_SHAH))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            306 ->{
                pD.add(PlaceDetails(getString(R.string.shilaidaha_rabindra_kuthibari)
                    ,getString(R.string.rabindar_kutibari_description),
                    getString(R.string.kushtia),
                    L.LAT_RABINDAR_KUTIBARI,
                    L.LONG_RABINDAR_KUTIBARI,
                    PIL.RABINDAR_KUTIBARI))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            401 ->{
                pD.add(PlaceDetails(getString(R.string.place_name_puthia_temple_complex)
                    ,getString(R.string.place_description_puthia_temple_complex),
                    getString(R.string.rajshahi),
                    L.LAT_PUTHIA_TEMPLE_COMPLEX,
                    L.LONG_PUTHIA_TEMPLE_COMPLEX,
                    PIL.IMAGE_LINK_PUTHIA_TEMPLE_COMPLEX))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            402 ->{
                pD.add(PlaceDetails(getString(R.string.place_name_ruins_buddhist_vihara)
                    ,getString(R.string.place_description_ruins_buddhist_vihara),
                    getString(R.string.rajshahi),
                    L.LAT_RUINS_OF_THE_BUDDHIST_VIHARA,
                    L.LONG_RUINS_OF_THE_BUDDHIST_VIHARA,
                    PIL.IMAGE_LINK_RUINS_BUDDHIST_VIHARA))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            403 ->{
                pD.add(PlaceDetails(getString(R.string.place_name_mohasthan_garh)
                    ,getString(R.string.place_description_mohasthan_garh),
                    getString(R.string.bogura),
                    L.LAT_MOHASTHAN_GARH,
                    L.LONG_MOHASTHAN_GARH,
                    PIL.IMAGE_LINK_MOHASTHAN_GARH))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            404 ->{
                pD.add(PlaceDetails(getString(R.string.place_name_choto_shona_mosque)
                    ,getString(R.string.place_description_choto_shona_mosque),
                    getString(R.string.rajshahi),
                    L.LAT_CHOTO_SHONA_MOSQUE,
                    L.LONG_CHOTO_SHONA_MOSQUE,
                    PIL.IMAGE_LINK_CHOTO_SHONA_MOSQUE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            405 ->{
                pD.add(PlaceDetails(getString(R.string.place_name_varendra_research_museum)
                    ,getString(R.string.place_description_varendra_research_museum),
                    getString(R.string.rajshahi),
                    L.LAT_VARENDA_RESEARCH_MUSEUM,
                    L.LONG_VARENDA_RESEARCH_MUSEUM,
                    PIL.IMAGE_URL_VARENDA_RESEARCH_MUSEUM))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            406 ->{
                pD.add(PlaceDetails(getString(R.string.place_name_shahid_zia_shishu_park)
                    ,getString(R.string.place_description_shahid_zia_shishu_park),
                    getString(R.string.rajshahi),
                    L.LAT_SHAHID_ZIA_SHISHU_PARK,
                    L.LONG_SHAHID_ZIA_SHISHU_PARK,
                    PIL.IMAGE_URL_SHAHID_ZIA_SHISHU_PARK))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            //BARISHAL
            501->{
                pD.add(PlaceDetails(getString(R.string.place_baitul_aman_jame_masjid)
                    ,getString(R.string.description_baitul_aman_jame_masjid),
                    getString(R.string.barisal),
                    L.LAT_BAITUL_AMAN_JAME_MASJID,
                    L.LONG_BAITUL_AMAN_JAME_MASJID,
                    PIL.IMAGE_BAITUL_AMAN_JAME_MASJID))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            502->{
                pD.add(PlaceDetails(getString(R.string.place_kuakata_sea_beach)
                    ,getString(R.string.description_kuakata_sea_beach),
                    getString(R.string.patuakhali),
                    L.LAT_KUAKATA_SEA_BEACH,
                    L.LONG_KUAKATA_SEA_BEACH,
                    PIL.IMAGE_KUAKATA_SEA_BEACH))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            503->{
                pD.add(PlaceDetails(getString(R.string.place_floating_guava_market)
                    ,getString(R.string.description_floating_guava_market),
                    getString(R.string.jhalokathi),
                    L.LAT_FLOATING_GUAVA_MARKET,
                    L.LONG_FLOATING_GUAVA_MARKET,
                    PIL.IMAGE_FLOATING_GUAVA_MARKET))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            504->{
                pD.add(PlaceDetails(getString(R.string.place_jakob_tower)
                    ,getString(R.string.description_jakob_tower),
                    "Bhola",
                    L.LAT_JAKOB_TOWER,
                    L.LONG_JAKOB_TOWER,
                    PIL.IMAGE_JAKOB_TOWER))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            505->{
                pD.add(PlaceDetails(getString(R.string.place_name_30_godown_monument)
                    ,getString(R.string.place_description_30_godown_monument),
                    getString(R.string.barisal),
                    L.LAT_30_GODOWN_MONUMENT,
                    L.LONG_30_GODOWN_MONUMENT,
                    PIL.IMAGE_30_GODOWN_MONUMENT))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            506->{
                pD.add(PlaceDetails(getString(R.string.place_name_bibir_pukur)
                    ,getString(R.string.place_description_bibir_pukur),
                    getString(R.string.barisal),
                    L.LAT_BIBIR_PUKUR,
                    L.LONG_BIBIR_PUKUR,
                    PIL.IMAGE_BIBIR_PUKUR))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            507->{
                pD.add(PlaceDetails(getString(R.string.place_name_freedom_fighters_park)
                    ,getString(R.string.place_description_freedom_fighters_park),
                    getString(R.string.barisal),
                    L.LAT_FREEDOM_FIGHTERS_PARK,
                    L.LONG_FREEDOM_FIGHTERS_PARK,
                    PIL.IMAGE_FREEDOM_FIGHTERS_PARK))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }


            //SYLHET
            601->{
                pD.add(PlaceDetails(getString(R.string.place_title_hazrat_shahjalal_mazar)
                    ,getString(R.string.place_description_hazrat_shahjalal_mazar),
                    getString(R.string.sylhet),
                    L.LAT_HAZRAT_SHAHJALAL_MAZAR,
                    L.LONG_HAZRAT_SHAHJALAL_MAZAR,
                    PIL.IMAGE_HAZRAT_SHAHJALAL_MAZAR))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            602->{
                pD.add(PlaceDetails(getString(R.string.place_title_sada_pathor)
                    ,getString(R.string.place_description_sada_pathor),
                    getString(R.string.sylhet),
                    L.LAT_SADA_PATHOR,
                    L.LONG_SADA_PATHOR,
                    PIL.IMAGE_SADA_PATHOR))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            603->{
                pD.add(PlaceDetails(getString(R.string.place_title_jaflong)
                    ,getString(R.string.place_description_jaflong),
                    getString(R.string.sylhet),
                    L.LAT_JAFLONG,
                    L.LONG_JAFLONG,
                    PIL.IMAGE_JAFLONG))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            604->{
                pD.add(PlaceDetails(getString(R.string.place_title_lalakhal)
                    ,getString(R.string.place_description_lalakhal),
                    getString(R.string.sylhet),
                    L.LAT_LALAKHAL,
                    L.LONG_LALAKHAL,
                    PIL.IMAGE_LALAKHAL))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            605->{
                pD.add(PlaceDetails(getString(R.string.place_title_ratargul_swamp_forest)
                    ,getString(R.string.place_description_ratargul_swamp_forest),
                    getString(R.string.sylhet),
                    L.LAT_RATARGUL_SWAMP_FOREST,
                    L.LONG_RATARGUL_SWAMP_FOREST,
                    PIL.IMAGE_RATARGUL_SWAMP_FOREST))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            606->{
                pD.add(PlaceDetails(getString(R.string.place_title_malnichhera_tea_garden)
                    ,getString(R.string.place_description_malnichhera_tea_garden),
                    getString(R.string.sylhet),
                    L.LAT_MALNICHHERA_TEA_GARDEN,
                    L.LONG_MALNICHHERA_TEA_GARDEN,
                    PIL.IMAGE_MALNICHHERA_TEA_GARDEN))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            607->{
                pD.add(PlaceDetails(getString(R.string.place_title_tanguar_haor)
                    ,getString(R.string.place_tanguar_haor_description),
                   getString(R.string.shunamganj),
                    L.LAT_TANGUAR_HAOR,
                    L.LONG_TANGUAR_HAOR,
                    PIL.IMAGE_TANGUAR_HAOR))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            //RANGPUR
            701->{
                pD.add(PlaceDetails(getString(R.string.place_tajhat_palace)
                    ,getString(R.string.place_tajhat_palace_description),
                    getString(R.string.rangpur),
                    L.TAJHAT_PALACE_LAT,
                    L.TAJHAT_PALACE_LONG,
                    PIL.IMAGE_TAJHAT_PALACE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            702->{
                pD.add(PlaceDetails(getString(R.string.place_kantajew_temple)
                    ,getString(R.string.place_kantajew_temple_description),
                    getString(R.string.rangpur),
                    L.KANTAJEW_TEMPLE_LAT,
                    L.KANTAJEW_TEMPLE_LONG,
                    PIL.IMAGE_KANTAJEW_TEMPLE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            703->{
                pD.add(PlaceDetails(getString(R.string.place_shopnopuri_amusement_park)
                    ,getString(R.string.place_shopnopuri_amusement_park_description),
                    getString(R.string.rangpur),
                    L.SHOPNOPURI_AMUSEMENT_PARK_LAT,
                    L.SHOPNOPURI_AMUSEMENT_PARK_LONG,
                    PIL.IMAGE_SHOPNOPURI_AMUSEMENT_PARK))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            704->{
                pD.add(PlaceDetails(getString(R.string.place_name_rangpur_town_hall)
                    ,getString(R.string.place_description_rangpur_town_hall),
                    getString(R.string.rangpur),
                    L.RANGUR_TOWN_HALL_LAT,
                    L.RANGPUR_TOWN_HALL_LONG,
                    PIL.IMAGE_RANGPUR_TOWN_HALL))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            705->{
                pD.add(PlaceDetails(getString(R.string.place_name_rangpur_zoo)
                    ,getString(R.string.place_description_rangpur_zoo),
                    getString(R.string.rangpur),
                    L.RANGPUR_ZOO_LAT,
                    L.RANGPUR_ZOO_LONG,
                    PIL.IAMGE_RANGPUR_ZOO))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            706->{
                pD.add(PlaceDetails(getString(R.string.palce_name_nayabad_mosque)
                    ,getString(R.string.palce_description_nayabad_mosque),
                    getString(R.string.dinajpur),
                    L.NAYABAD_MOSQUE_LAT,
                    L.NAYABAD_MOSQUE_LONG,
                    PIL.IMAGE_NAYABAD_MOSQUE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            //MYMENTSING
            801->{
                pD.add(PlaceDetails(getString(R.string.title_madhutila_eco_park)
                    ,getString(R.string.description_madhutila_eco_park),
                    getString(R.string.mymensingh),
                    L.LAT_MADHUTILA_ECO_PARK,
                    L.LONG_MADHUTILA_ECO_PARK,
                    PIL.IMAGE_URL_MADHUTILA_ECO_PARK))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            802->{
                pD.add(PlaceDetails(getString(R.string.title_china_matir_pahar)
                    ,getString(R.string.description_china_matir_pahar),
                    getString(R.string.mymensingh),
                    L.LAT_CHINA_MATIR_PAHAR,
                    L.LONG_CHINA_MATIR_PAHAR,
                    PIL.IMAGE_URL_CHINA_MATIR_PAHAR))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            803->{
                pD.add(PlaceDetails(getString(R.string.place_name_shashi_lodge)
                    ,getString(R.string.place_description_shashi_lodge),
                    getString(R.string.mymensingh),
                    L.LAT_SHASHI_LODGE,
                    L.LONG_SHAHID_ZIA_SHISHU_PARK,
                    PIL.IMAGE_URL_SHASHI_LODGE))
                val action = PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            804-> {
                pD.add(
                    PlaceDetails(
                        getString(R.string.place_name_luis_village_resort),
                        getString(R.string.place_description_luis_village_resort),
                        getString(R.string.mymensingh),
                        L.LAT_LUIS_VILLAGE_RESORT,
                        L.LONG_LUIS_VILLAGE_RESORT,
                        PIL.IMAGE_URL_LUIS_VILLAGE_RESORT
                    )
                )
                val action =
                    PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }

            805-> {
                pD.add(
                    PlaceDetails(
                        getString(R.string.place_name_muktagacha_zamindar_bari),
                        getString(R.string.place_description_muktagacha_zamindar_bari),
                        getString(R.string.mymensingh),
                        L.LAT_MUKTAGACHA_ZAMINDAR_BARI,
                        L.LONG_MUKTAGACHA_ZAMINDAR_BARI,
                        PIL.IMAGE_URL_MUKTAGACHA_ZAMINDAR_BARI
                    )
                )
                val action =
                    PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(pD[0])
                pD.clear()
                findNavController().navigate(action)
            }
            else ->{
                Toast.makeText(requireContext(),getString(R.string.this_feature_is_under_development),Toast.LENGTH_SHORT).show()
            }
        }
    }

}