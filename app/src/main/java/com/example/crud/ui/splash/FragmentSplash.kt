package com.example.crud.ui.splash

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FragmentSplash : BaseFragmentWithBinding<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private var isFragmentAttached = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the status bar color
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        isFragmentAttached = true

        Glide.with(this).asGif().load(R.drawable.splash).into(binding.loader)

        navigateDashboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFragmentAttached = false
    }

    private fun navigateDashboard() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            if (isFragmentAttached) {
                withContext(Dispatchers.Main) {
                    findNavController().navigate(R.id.login)
                }
            }
        }
    }
}
