package com.example.crud.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentWebViewBinding
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.showCustomToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class WebView : BaseFragmentWithBinding<FragmentWebViewBinding>
    (FragmentWebViewBinding::inflate){

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var searchLink = "https://www.google.com/maps/search/narby+hotels/"
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding.ivBackBtn.setOnClickListener{
            findNavController().popBackStack()
        }

        if (!CheckNetwork(requireContext()).isNetworkConnected){
            Toast(requireContext()).showCustomToast(getString(R.string.pls_turn_on_internet),requireActivity())
        }
        val mapType =   requireArguments().getString("type")
        if (mapType == "places"){
            searchLink = "https://www.google.com/maps/search/nearby+tourist+spot/"
        }

        getCurrentLocation()

    }

    private fun getCurrentLocation() {

        if (checkPermission()){
            if (isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    val location:Location?=it
                    if (location==null){
                        findNavController().popBackStack()
                        Toast(requireContext()).showCustomToast(getString(
                            R.string.something_went_wrong_try_again),requireActivity())
                    }else{
                        showMaps(location.latitude,location.longitude)
                    }
                }
            }
            else{
                //setting open here
                findNavController().popBackStack()
                Toast.makeText(requireContext(),getString(R.string.turn_on_location), Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else{
            //request permission here
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION){
            if (grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Log.e("nlog","Location Access Given")
                getCurrentLocation()
            }else{
                //show
                Log.e("nlog","Location Access Denied")
                requestPermission()
            }
        }
    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 7777
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