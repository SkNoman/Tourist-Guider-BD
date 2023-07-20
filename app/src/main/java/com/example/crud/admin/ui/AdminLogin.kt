package com.example.crud.admin.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.crud.R
import com.example.crud.admin.model.Admin
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentAdminLoginBinding
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.showCustomToast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminLogin : BaseFragmentWithBinding<FragmentAdminLoginBinding>(
    FragmentAdminLoginBinding::inflate)
{
    private lateinit var mDbRef: DatabaseReference
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        mDbRef = FirebaseDatabase.getInstance().reference

        binding.btnLogin.setOnClickListener{
            if (CheckNetwork(requireContext()).isNetworkConnected){
                if (validateLogin() == "OK"){
                    binding.progressBarLogin.visibility = View.VISIBLE
                    adminLogin(binding.etUsernameLogin.text.toString(),
                        binding.etPassLogin.text.toString())
                }else{
                    Toast.makeText(requireContext(),validateLogin(), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast(requireContext()).showCustomToast(getString(
                    R.string.please_turn_on_internet_connection),requireActivity())
            }
        }

    }

    private fun adminLogin(username:String,pass: String) {
        mDbRef.child("admins").child(username).get().addOnSuccessListener {
            if (it.exists()){
                binding.progressBarLogin.visibility = View.GONE
                val adminInfo = it.getValue(Admin::class.java)
                Log.e("nlog-admin",adminInfo?.pass.toString())
                Log.e("nlog-admin",adminInfo?.email.toString())
                if (adminInfo?.pass == pass){
                    val bundle = Bundle()
                    bundle.putString("admin_name",username)
                    findNavController().navigate(R.id.adminDashboard,bundle)
                    binding.etPassLogin.text = null
                    binding.etUsernameLogin.text = null
                    binding.progressBarLogin.visibility = View.GONE
                    Toast.makeText(requireContext(),"Login Success",Toast.LENGTH_SHORT).show()
                }else{
                    binding.progressBarLogin.visibility = View.GONE
                    Toast.makeText(requireContext(),"Please enter correct password",Toast.LENGTH_SHORT).show()
                }

            }else{
                binding.progressBarLogin.visibility = View.GONE
                Toast(requireContext()).showCustomToast("Sorry No User Found",requireActivity())
            }
        }.addOnFailureListener{
            binding.progressBarLogin.visibility = View.GONE
            Toast(requireContext()).showCustomToast("Error getting data $it",requireActivity())
        }
    }



    private fun validateLogin():String {
        if (binding.etUsernameLogin.text.isNullOrEmpty()){
            return "Please enter username"
        }else if(binding.etUsernameLogin.text!!.length <2){
            return "Please enter valid username"
        }else if(binding.etPassLogin.text.isNullOrEmpty()){
            return "Please enter password"
        }else if (binding.etPassLogin.text!!.length < 6){
            return "Password minimum length is 6"
        }
        return "OK"
    }
}