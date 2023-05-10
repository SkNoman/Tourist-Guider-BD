package com.example.crud.ui.divisions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentChittagongBinding


class ChittagongFragment : BaseFragmentWithBinding<FragmentChittagongBinding>
    (FragmentChittagongBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtChittagong.text = getString(R.string.chittagong_division)
    }
}