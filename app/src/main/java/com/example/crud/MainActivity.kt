package com.example.crud

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.crud.databinding.ActivityMainBinding
import com.example.crud.utils.GoogleMaps
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment_content_main)
       /* window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
           WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)*/
        drawerLayout = binding.drawerLayout
        //OPEN NAV DRAWER FROM CUSTOM TOOLBAR BUTTON
        binding.btnMenu.setOnClickListener {
            val drawerLayout = binding.drawerLayout
            drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.switchLanguage.setOnClickListener{
            if (binding.switchLanguage.text == "Bangla") {
                Log.e("nlog","yes")
                setAppLanguage("bn")
            }else{
                setAppLanguage("en")
            }
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navView: NavigationView = binding.navigationView
        navView.setCheckedItem(R.id.home)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.maps -> {
                    GoogleMaps.openGoogleMaps(this,23.8103,90.4125)
                }
                R.id.about_us -> {
                    navController.navigate(R.id.aboutFragment)
                }
                R.id.restaurants_cafes ->{
                    val bundle = Bundle()
                    bundle.putString("type","cafes")
                    navController.navigate(R.id.webView2,bundle)
                }
                R.id.nearby_attractions -> {
                    val bundle = Bundle()
                    bundle.putString("type","places")
                    navController.navigate(R.id.webView2,bundle)
                }
                else -> {
                    Toast.makeText(this,getString(R.string.this_feature_is_under_development),Toast.LENGTH_SHORT).show()
                }
            }
            true
        }


// CHANGE THE VIEW OR ACTIVITIES IN DIFFERENT FRAGMENTS
        navController.addOnDestinationChangedListener{controller,destination,arguments ->
            when(destination.id){
                R.id.fragmentSplash ->{
                    binding.toolbar.visibility = View.GONE
                }
                R.id.fragmentDashboard -> {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                binding.toolbar.visibility = View.VISIBLE
                }
                else ->{
                    binding.toolbar.visibility = View.GONE
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        }

    }

    private fun setAppLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration()
        configuration.locale = locale

        resources.updateConfiguration(configuration, resources.displayMetrics)
        recreate()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        try {
            when (navController.currentDestination?.id) {
                R.id.fragmentDashboard -> {
                    finish()
                }
                else -> {
                    super.onBackPressed()
                }
            }
        }catch (e:Exception){
            Log.e("nlog-exc-BackPressed",e.toString())
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}