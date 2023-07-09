package com.example.crud.ui.login_sign_up

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentValidateOTPBinding
import com.example.crud.model.Users
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.SharedPref
import com.example.crud.utils.showCustomToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ValidateOTP : BaseFragmentWithBinding<FragmentValidateOTPBinding>(
    FragmentValidateOTPBinding::inflate
)
{
    private lateinit var token: PhoneAuthProvider.ForceResendingToken
    private lateinit var OTP:String
    private lateinit var name:String
    private lateinit var email:String
    private lateinit var phone: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference
        try {
            OTP = requireArguments().getString("OTP").toString()
            name = requireArguments().getString("name").toString()
            phone = requireArguments().getString("phone").toString()
            email = requireArguments().getString("email").toString()
            password = requireArguments().getString("pass").toString()
            token = requireArguments().getString("resendToken").toString() as ForceResendingToken
        }catch (e:Exception){
            Log.e("nlog",e.toString())
        }

        if(CheckNetwork(requireContext()).isNetworkConnected){
            binding.verifyOtp.setOnClickListener{
                val typedOtp = binding.pinview.text
                if (typedOtp!!.isNotEmpty()){
                    if (typedOtp.length == 6){
                        binding.progressBar.visibility = View.VISIBLE
                        val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                            OTP, typedOtp.toString()
                        )
                        signInWithPhoneAuthCredential(credential)
                    }else{
                        Toast.makeText(requireContext(),"Please enter full OTP",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(),"Please enter otp",Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast(requireContext()).showCustomToast(getString
                (R.string.please_turn_on_internet_connection),requireActivity())
        }

    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(),"Validated successfully",Toast
                        .LENGTH_SHORT).show()
                    signUp(name, phone, email, password)

                } else {

                    Toast.makeText(requireContext(),"Please enter valid OTP",Toast
                        .LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
    }



    private fun signUp(name:String,phone:String,email:String,password:String) {


        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,phone,email,auth.currentUser?.uid!!)
                    SharedPref.sharedPrefManger(requireContext(),true,"isFromLogin")
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Welcome", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.fragmentDashboard)
                } else {
                     binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Sign up Failed: ${task.exception}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, phone: String, email: String, uid: String) {
        dbRef.child("users").child(uid).setValue(
            Users(name,phone,email,uid)
        )
    }
}