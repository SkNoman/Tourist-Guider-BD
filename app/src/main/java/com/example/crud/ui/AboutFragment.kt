package com.example.crud.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentAboutBinding

class AboutFragment : BaseFragmentWithBinding<FragmentAboutBinding>
    (FragmentAboutBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivBackBtn.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}