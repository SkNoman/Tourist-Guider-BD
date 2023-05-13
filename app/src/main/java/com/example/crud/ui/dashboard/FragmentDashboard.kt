package com.example.crud.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import androidx.viewpager2.widget.ViewPager2
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentUserDashboardBinding
import com.example.crud.model.SlideItem
import com.example.crud.model.dashboard.FeaturedItem
import com.example.crud.model.dashboard.MenusItem
import com.example.crud.ui.adapters.DashboardMainMenuAdapter
import com.example.crud.ui.adapters.FeaturedListItemAdapter
import com.example.crud.ui.adapters.OnClickMenu
import com.example.crud.ui.adapters.SlideItemAdapter
import com.example.crud.utils.PIL
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class FragmentDashboard : BaseFragmentWithBinding<FragmentUserDashboardBinding>
    (FragmentUserDashboardBinding:: inflate),OnClickMenu,OnRefreshListener {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var swipeLayout: SwipeRefreshLayout

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(viewPagerHotItemRunnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(viewPagerHotItemRunnable, 2000)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            autoPlaceSlider()
        }
        featuredRecycler()
        swipeLayout = binding.layoutDashboard
        swipeLayout.setOnRefreshListener(this)
        setMenus()
        showNearbyHotels()
    }

    private fun showNearbyHotels() {
        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        binding.webView.webViewClient = WebViewClient()
        // this will load the url of the website
        binding.webView.loadUrl("https://www.google.com/maps/search/narby+hotels/@23.7969814,90.4060102,")
        // this will enable the javascript settings, it can also allow xss vulnerabilities
        binding.webView.settings.javaScriptEnabled = true
        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(true)
    }

    private fun setMenus() {
        val menusItem: MutableList<MenusItem> = mutableListOf()
        // Add items to the menusItem list
        menusItem.add(MenusItem(1, getString(R.string.dhaka),R.drawable.dhaka))
        menusItem.add(MenusItem(2,getString(R.string.chittagong),R.drawable.chittagong))
        menusItem.add(MenusItem(3,getString(R.string.khulna),R.drawable.khulna))
        menusItem.add(MenusItem(4,getString(R.string.rajshahi),R.drawable.rajshahi))
        menusItem.add(MenusItem(5,getString(R.string.barisal),R.drawable.barisal))
        menusItem.add(MenusItem(6,getString(R.string.sylhet),R.drawable.sylhet))
        menusItem.add(MenusItem(7,getString(R.string.rangpur),R.drawable.rangpur))
        menusItem.add(MenusItem(8,getString(R.string.mymensingh),R.drawable.mymensing))
        showMenus(menusItem)
    }

    val viewPagerHotItemRunnable = object : Runnable {
        override fun run() {
            binding.viewPagerHotItem.currentItem = binding.viewPagerHotItem.currentItem + 1
            handler.postDelayed(this, 2000)
        }
    }

    private fun autoPlaceSlider() {
        val sliderItem :ArrayList<SlideItem> = ArrayList()
        sliderItem.add(SlideItem("https://images.pexels.com/photos/3560020/pexels-photo-3560020.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"))
        sliderItem.add(SlideItem("https://images.unsplash.com/photo-1549300461-11c5b94e8855?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"))
        sliderItem.add(SlideItem("https://images.unsplash.com/photo-1608958435020-e8a7109ba809?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1032&q=80"))

        sliderItem.add(SlideItem(PIL.PLACE_HATIR_JHEEL_IMAGE))
        sliderItem.add(SlideItem(PIL.COXS_BAZAR_SEA_BEACH))
        sliderItem.add(SlideItem(PIL.NAFA_KHUM_WATERFALL))
        sliderItem.add(SlideItem(PIL.CHANDRANATH_HILLS))
        sliderItem.add(SlideItem(PIL.PLACE_TAJ_MAHAL_BANGLADESH_IMAGE))

        binding.viewPagerHotItem.apply {
            adapter = SlideItemAdapter(requireContext(),sliderItem)
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = sliderItem.size
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        /*   val compositionTransformer = CompositePageTransformer()
           compositionTransformer.addTransformer(MarginPageTransformer(10))
           compositionTransformer.addTransformer { page, position ->
               val r = 1 - kotlin.math.abs(position)
               page.scaleY = 0.80f + r * 01.1f
           }
           binding.viewPager.setPageTransformer(compositionTransformer)*/

        binding.viewPagerHotItem.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                handler.removeCallbacks(viewPagerHotItemRunnable)
                handler.postDelayed(viewPagerHotItemRunnable, 2000)
            }
        })
    }

    private  fun featuredRecycler() {
        binding.featuredRecyclerView.setHasFixedSize(true)
        binding.featuredRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val featuredLocations: ArrayList<FeaturedItem> = ArrayList()
        //MANUAL ADDITION OF RECYCLER CARD VIEW
        featuredLocations.add(
            FeaturedItem(
                PIL.NAFA_KHUM_WATERFALL,
                "Nafa Khum",
                "The best water fall"
            )
        )
        featuredLocations.add(
            FeaturedItem(
                PIL.COXS_BAZAR_SEA_BEACH,
                "Cox Bazar",
                "Longest sea beach"
            )
        )
        featuredLocations.add(
            FeaturedItem(
                PIL.PLACE_HATIR_JHEEL_IMAGE,
                "Hatir Jheel",
                "Best jheel in dhaka"
            )
        )
        binding.featuredRecyclerView.layoutManager =
            LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        binding.featuredRecyclerView.adapter =
            FeaturedListItemAdapter(requireContext(),featuredLocations)
    }

    private fun showMenus(menusItem: List<MenusItem>) {
        binding.recyclerviewMainMenu.layoutManager =
            GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
        binding.recyclerviewMainMenu.adapter =
            DashboardMainMenuAdapter(requireContext(),menusItem,this)
    }

    override fun onClick(id: Int) {
        //EACH ID REPRESENTS DIFFERENT FEATURE WHICH IS DEFINED IN DATABASE
        val bundle = Bundle()

        when(id){

            1->{
                bundle.putInt("divisionId",1)
                findNavController().navigate(R.id.action_fragmentDashboard_to_placesListFragment,bundle)
            }
            2->{
                bundle.putInt("divisionId",2)
                findNavController().navigate(R.id.action_fragmentDashboard_to_placesListFragment,bundle)
            }
            3->{
                bundle.putInt("divisionId",3)
                findNavController().navigate(R.id.action_fragmentDashboard_to_placesListFragment,bundle)
            }
            4->{
                bundle.putInt("divisionId",4)
                findNavController().navigate(R.id.action_fragmentDashboard_to_placesListFragment,bundle)
            }
            5->{
                bundle.putInt("divisionId",5)
                findNavController().navigate(R.id.action_fragmentDashboard_to_placesListFragment,bundle)
            }
            6->{
                bundle.putInt("divisionId",6)
                findNavController().navigate(R.id.action_fragmentDashboard_to_placesListFragment,bundle)
            }
            7->{
                bundle.putInt("divisionId",7)
                findNavController().navigate(R.id.action_fragmentDashboard_to_placesListFragment,bundle)
            }
            8->{
                bundle.putInt("divisionId",8)
                findNavController().navigate(R.id.action_fragmentDashboard_to_placesListFragment,bundle)
            }
        }
    }

    override fun onRefresh() {
        swipeLayout.isRefreshing = false
    }
}