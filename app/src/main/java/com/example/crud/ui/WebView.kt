package com.example.crud.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentWebViewBinding

class WebView : BaseFragmentWithBinding<FragmentWebViewBinding>
    (FragmentWebViewBinding::inflate){
    var searchLink = "https://www.google.com/maps/search/narby+hotels/"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivBackBtn.setOnClickListener{
            findNavController().popBackStack()
        }

        val mapType =   requireArguments().getString("type")
        if (mapType == "places"){
            searchLink = "https://www.google.com/maps/search/nearby+tourist+spot/"
        }
        val lat = 23.8103
        val long = 90.4125
        showMaps(lat,long)
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