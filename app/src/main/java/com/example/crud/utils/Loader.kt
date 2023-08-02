package com.example.crud.utils

import com.example.crud.R
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatDialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import com.example.crud.databinding.LoaderDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@AndroidEntryPoint
class Loader (onClick: OnClick): AppCompatDialogFragment() {
    private var binding: LoaderDialogBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = LoaderDialogBinding.inflate(
            LayoutInflater.from(
                context
            )
        )
        val builder = context?.let { MaterialAlertDialogBuilder(it, R.style.MaterialAlertDialog_Rounded) }
        builder?.background = ColorDrawable(Color.TRANSPARENT)
        builder?.setView(binding!!.root)
        return builder!!.create()
    }
    interface OnClick{
        fun onClick(){

        }
    }
}