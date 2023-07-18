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
import com.example.crud.admin.model.PlaceName
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentPlacesListBinding
import com.example.crud.model.PlaceDetails
import com.example.crud.model.Users
import com.example.crud.model.menu.PlaceListItem
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

    private val list: MutableList<PlaceName> = mutableListOf()
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
                binding.txtPlaceBannerName.text = getString(R.string.khulna_division)

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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPlaces(division: String) {
        if (CheckNetwork(requireContext()).isNetworkConnected){
            dbRef.child("places").child(division).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                       /* for (placeSnapshot in snapshot.children) {
                            val place = placeSnapshot.getValue(PlaceName::class.java)
                            place?.let {
                                Log.e("nlog-it",it.placeDetails?.name.toString())
                                list.add(it)
                            }
                        }*/
                        val place = snapshot.getValue(PlaceName::class.java)

                        Log.e("nlog",place?.placeDetails?.name.toString())

                           /* Log.e("nlog",list[0].name.toString())
                           Log.e("nlog-e",list[0].placeDetails.toString())*/



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


    private fun showPlaceList(list: List<PlaceName>) {
        binding.recyclerViewDhakaDivision.adapter =
            PlaceListAdapter(requireContext(), list,this)
        }

    fun filter(text: String?) {
        val temp: MutableList<PlaceName> = ArrayList()

        for (s in list) {
            if (s.name!!.contains(text!!,true)) {
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