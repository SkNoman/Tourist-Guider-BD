package com.example.crud

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.crud.databinding.ActivityMainBinding
import com.example.crud.utils.SharedPref
import com.example.crud.utils.ToolbarCallback
import com.example.crud.utils.showCustomToast
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),ToolbarCallback {

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
                setAppLanguage("en")
            }else{
                setAppLanguage("bn")
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
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setPackage("com.google.android.apps.maps")
                    startActivity(intent)
                }
                R.id.about_us -> {
                    navController.navigate(R.id.aboutFragment)
                }
                R.id.restaurants_cafes ->{
                    if (isLocationEnabled()){
                        if (checkPermission()){
                            val bundle = Bundle()
                            bundle.putString("type","cafes")
                            navController.navigate(R.id.webView2,bundle)
                        }else{
                            requestPermission()
                        }

                    }else{
                        Toast(this).showCustomToast(getString(R.string.turn_on_location),this)
                    }

                }
                R.id.nearby_attractions -> {
                    if (isLocationEnabled()){
                        if (checkPermission()){
                            val bundle = Bundle()
                            bundle.putString("type","places")
                            navController.navigate(R.id.webView2,bundle)
                        }else{
                            requestPermission()
                        }

                    }else{
                        Toast(this).showCustomToast(getString(R.string.turn_on_location),this)
                    }
                }
                R.id.travel_tips ->{
                    navController.navigate(R.id.tipsFragment)
                }
                R.id.sign_out ->{
                   val auth = FirebaseAuth.getInstance()
                    auth.signOut()
                    SharedPref.sharedPrefManger(this,"","email")
                    SharedPref.sharedPrefManger(this,"","password")
                    navController.navigate(R.id.login)
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
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION),
            7777
        )
    }
    private fun isLocationEnabled():Boolean{
        val locationManager: LocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }
    private fun checkPermission():Boolean{
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            return true
        }
        return false
    }

    private fun setAppLanguage(languageCode: String) {
        SharedPref.sharedPrefManger(this,true,"recreated")
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

    override fun updateToolbarMsg(msg: String) {
        binding.textView.text = msg
    }

}