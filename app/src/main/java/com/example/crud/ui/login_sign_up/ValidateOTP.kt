package com.example.crud.ui.login_sign_up

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentValidateOTPBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken


class ValidateOTP : BaseFragmentWithBinding<FragmentValidateOTPBinding>(
    FragmentValidateOTPBinding::inflate
)
{
    private lateinit var token: PhoneAuthProvider.ForceResendingToken
    private lateinit var OTP:String
    private lateinit var auth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        try {
            OTP = requireArguments().getString("OTP").toString()
            token = requireArguments().getString("resendToken").toString() as ForceResendingToken
        }catch (e:Exception){
            Log.e("nlog",e.toString())
        }

        binding.verifyOtp.setOnClickListener{
            val typedOtp = binding.pinview.text
            if (typedOtp!!.isNotEmpty()){
                if (typedOtp.length == 6){
                    val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP, typedOtp.toString()
                    )
                    signInWithPhoneAuthCredential(credential)
                }else{
                    Toast.makeText(requireContext(),"Please enter correct OTP",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"Please enter otp",Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(),"Phone number validated successfully",Toast
                        .LENGTH_SHORT).show()
                    findNavController().navigate(R.id.login)
                } else {
                    Toast.makeText(requireContext(),getString(R.string.something_went_wrong),Toast
                        .LENGTH_SHORT).show()
                }
            }
    }
}