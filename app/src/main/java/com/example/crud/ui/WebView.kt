package com.example.crud.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.crud.BuildConfig
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentWebViewBinding
import com.example.crud.utils.FusedLocation
import com.example.crud.utils.showCustomToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class WebView : BaseFragmentWithBinding<FragmentWebViewBinding>
    (FragmentWebViewBinding::inflate){
    var searchLink = "https://www.google.com/maps/search/narby+hotels/"
    private var lat:Double = 0.0
    private var long:Double = 0.0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivBackBtn.setOnClickListener{
            findNavController().popBackStack()
        }

        val mapType =   requireArguments().getString("type")
        if (mapType == "places"){
            searchLink = "https://www.google.com/maps/search/nearby+tourist+spot/"
        }


        if (!checkPermission()){
            Log.e("is_enter","yes")
            Toast.makeText(requireContext(),getString(R.string.location_access_required), Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts(
                "package",
                BuildConfig.APPLICATION_ID,
                null
            )
            intent.data = uri
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        if (checkPermission()){
            if (!isLocationEnabled()){
                findNavController().popBackStack()
                Toast.makeText(requireContext(),getString(R.string.turn_on_location), Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }else{
                //everything fine then get lat long
                getLatLong()
            }
        }


//        lat = 23.8103
//        long = 90.4125
        //showMaps(lat,long)
    }


    private  fun getLatLong(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationProviderClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                try {
                    Log.d("latLongFused", "Lat: ${it.latitude}, Long: ${it.longitude}")
                    lat = it.latitude
                    long = it.longitude
                    if (lat != 0.0){
                        showMaps(lat,long)
                    }else{
                        getLatLong()
                    }
                } catch (e: Exception) {
                    Log.d("latLongException", e.toString())
                }
            }
        }

    }
    private fun isLocationEnabled():Boolean{
        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }
    private fun checkPermission():Boolean{
        if(ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            return true
        }
        return false
    }






    @SuppressLint("SetJavaScriptEnabled")
    private fun showMaps(lat: Double, long: Double) {
        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        binding.webView.webViewClient = WebViewClient()
        // this will load the url of the website
        binding.webView.loadUrl("$searchLink,$lat,$long")
        // this will enable the javascript settings, it can also allow xss vulnerabilities
        binding.webView.settings.javaScriptEnabled = true
        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(true)
    }

}