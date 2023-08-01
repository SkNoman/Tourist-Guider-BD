package com.example.crud.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
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