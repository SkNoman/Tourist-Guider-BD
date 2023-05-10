package com.example.crud.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
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
import com.example.crud.utils.GoogleMaps
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Suppress("DEPRECATION")
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
            Log.e("Thread-Long-Run2",Thread.currentThread().name)
            autoSliderHotItem()
        }
        featuredRecycler()
        swipeLayout = binding.layoutDashboard
        swipeLayout.setOnRefreshListener(this)

        setMenus()

//        binding.txtTrendingNow.setOnClickListener{
//            GoogleMaps.openGoogleMaps(requireActivity(),23.9756079,90.394622)
//        }
        binding.bottomNavigation.setOnNavigationItemReselectedListener { item ->
            when(item.itemId) {
                R.id.item1 -> {
                    Toast.makeText(requireContext(),getString(R.string.this_feature_is_under_development),Toast.LENGTH_SHORT).show()
                }
                R.id.item2 -> {
                    Toast.makeText(requireContext(),getString(R.string.this_feature_is_under_development),Toast.LENGTH_SHORT).show()
                }
                R.id.item3 ->{
                    Toast.makeText(requireContext(),getString(R.string.this_feature_is_under_development),Toast.LENGTH_SHORT).show()
                }
                R.id.item4 ->{
                    Toast.makeText(requireContext(),getString(R.string.this_feature_is_under_development),Toast.LENGTH_SHORT).show()
                }



            }
        }

    }

    private fun setMenus() {
        val menusItem: MutableList<MenusItem> = mutableListOf()
        // Add items to the menusItem list
        menusItem.add(MenusItem(1, getString(R.string.dhaka),R.drawable.google))
        menusItem.add(MenusItem(2,getString(R.string.chittagong),R.drawable.google))
        menusItem.add(MenusItem(3,getString(R.string.khulna),R.drawable.google))
        menusItem.add(MenusItem(4,getString(R.string.rajshahi),R.drawable.google))
        menusItem.add(MenusItem(5,getString(R.string.barisal),R.drawable.google))
        menusItem.add(MenusItem(6,getString(R.string.sylhet),R.drawable.google))
        menusItem.add(MenusItem(6,getString(R.string.rangpur),R.drawable.google))
        menusItem.add(MenusItem(6,getString(R.string.mymensingh),R.drawable.google))
        showMenus(menusItem)
    }

    val viewPagerHotItemRunnable = object : Runnable {
        override fun run() {
            binding.viewPagerHotItem.currentItem = binding.viewPagerHotItem.currentItem + 1
            handler.postDelayed(this, 2000)
        }
    }

    private fun autoSliderHotItem() {
        val sliderItem :ArrayList<SlideItem> = ArrayList()
        sliderItem.add(SlideItem("https://images.pexels.com/photos/3560020/pexels-photo-3560020.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"))
        sliderItem.add(SlideItem("https://images.unsplash.com/photo-1549300461-11c5b94e8855?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"))
        sliderItem.add(SlideItem("https://images.unsplash.com/photo-1608958435020-e8a7109ba809?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1032&q=80"))
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
                R.drawable.google,
                "Google",
                "The best search engine"
            )
        )
        featuredLocations.add(
            FeaturedItem(
                R.drawable.facebook,
                "Facebook",
                "Best social media platform"
            )
        )
        featuredLocations.add(
            FeaturedItem(
                R.drawable.linkedin,
                "Linkedin",
                "Best professional media"
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
        when(id){
            1->{
                findNavController().navigate(R.id.action_fragmentDashboard_to_dhakaFragment)
            }
            2->{
                findNavController().navigate(R.id.action_fragmentDashboard_to_chittagongFragment)
            }
            else->{
                Toast.makeText(requireContext(),getString(R.string.this_feature_is_under_development),Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRefresh() {
        swipeLayout.isRefreshing = false
    }
}