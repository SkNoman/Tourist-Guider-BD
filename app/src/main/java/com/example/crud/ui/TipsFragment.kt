package com.example.crud.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentTipsBinding

class TipsFragment : BaseFragmentWithBinding<FragmentTipsBinding>(
    FragmentTipsBinding::inflate
)
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBackBtn.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}