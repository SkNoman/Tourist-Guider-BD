package com.example.crud.ui.login_sign_up


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentSingUpBinding
import com.example.crud.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUp : BaseFragmentWithBinding<FragmentSingUpBinding>(
    FragmentSingUpBinding::inflate
)
{
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference



        binding.btnSignUp.setOnClickListener{

            if (validateSignUp() == "OK"){
                binding.progressBar.visibility = View.VISIBLE
                signUp(binding.etFullName.text.toString(),
                    binding.etPhone.text.toString() ,
                    binding.etEmailSignUp.text.toString(),
                    binding.etPasswordSignUp.text.toString(),
                )
            }else{
                Toast.makeText(requireContext(),validateSignUp(), Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvLoginBack.setOnClickListener{
            findNavController().navigate(R.id.login)
        }
    }


    private fun signUp(name:String,phone:String,email:String,password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,phone,email,auth.currentUser?.uid!!)
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Sign up Successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.fragmentDashboard)
                } else {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Sign up Failed: ${task.exception}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, phone: String, email: String, uid: String) {
        dbRef.child("users").child(uid).setValue(
            User.Users(name,phone,email,uid))
    }

    private fun validateSignUp():String {
        if (binding.etFullName.text.isNullOrEmpty()){
            return "Please enter your name"
        }else if (binding.etPhone.text.isNullOrEmpty()){
            return "Please enter your phone number"
        }else if(binding.etPhone.text!!.length<11){
            return "Please enter valid phone number"
        }else if (binding.etEmailSignUp.text.isNullOrEmpty()){
            return "Please enter email"
        }else if(binding.etEmailSignUp.text!!.length < 15){
            return "Please enter valid email"
        }else if(binding.etPasswordSignUp.text.isNullOrEmpty()){
            return "Please enter password"
        }else if (binding.etPasswordSignUp.text!!.length < 6){
            return "Password minimum length is 6"
        }
        return "OK"
    }
}