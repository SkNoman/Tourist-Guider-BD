package com.example.crud.ui.login_sign_up


import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentLoginBinding
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.SharedPref
import com.example.crud.utils.Validation
import com.example.crud.utils.showCustomToast
import com.google.firebase.auth.FirebaseAuth


class Login : BaseFragmentWithBinding<FragmentLoginBinding>
    (FragmentLoginBinding::inflate)
{

    private lateinit var auth: FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


        binding.btnLogin.setOnClickListener{
            if (CheckNetwork(requireContext()).isNetworkConnected){
                if (validateLogin() == "OK"){
                    binding.progressBarLogin.visibility = View.VISIBLE
                    login(binding.etEmailLogin.text.toString(),
                        binding.etPassLogin.text.toString())
                }else {
                    Toast.makeText(requireContext(),validateLogin(),Toast.LENGTH_SHORT).show()

                }
          }else{
                Toast(requireContext()).showCustomToast(getString(
                    R.string.please_turn_on_internet_connection),requireActivity())
          }
        }

        binding.tvRegister.setOnClickListener{
            findNavController().navigate(R.id.signUp)
        }
        val localData = SharedPref.getData(requireContext())
        val email = localData.getString("email","")
        val pass =  localData.getString("password","")
        binding.etPassLogin.setText(pass)
        binding.etEmailLogin.setText(email)

    }

    private fun validateLogin():String {
        if (binding.etEmailLogin.text.isNullOrEmpty()){
            return "Please enter email"
        }else if(binding.etEmailLogin.text!!.length < 15){
            return "Please enter valid email"
        }else if(!Validation.emailValidation(binding.etEmailLogin.text.toString())){
            return "Please enter valid email format"
        }else if(binding.etPassLogin.text.isNullOrEmpty()){
            return "Please enter password"
        }else if (binding.etPassLogin.text!!.length < 6){
            return "Password minimum length is 6"
        }
        return "OK"
    }

    private fun login(email:String,password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    SharedPref.sharedPrefManger(requireContext(),true,"isFromLogin")
                    binding.progressBarLogin.visibility = View.GONE
                   Toast.makeText(requireContext(),"Welcome",Toast.LENGTH_SHORT).show()
                    if (binding.checkBoxRememberMe.isChecked){
                        saveLoginInfo(auth.currentUser?.uid!!,binding.etEmailLogin.text.toString()
                        ,binding.etPassLogin.text.toString())
                    }
                    binding.etPassLogin.text=null
                    binding.etEmailLogin.text=null
                    findNavController().navigate(R.id.fragmentDashboard)
                }else {
                    binding.progressBarLogin.visibility = View.GONE
                    Toast.makeText(requireContext(),"Something went wrong\n " +
                            "try again with correct credentials",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveLoginInfo(uid:String,email:String, password:String){
      SharedPref.sharedPrefManger(requireContext(),uid,"uid")
        SharedPref.sharedPrefManger(requireContext(),email,"email")
        SharedPref.sharedPrefManger(requireContext(),password,"password")
    }
}