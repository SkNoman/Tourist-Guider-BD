package com.example.crud.ui.login_sign_up


import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.crud.R
import com.example.crud.base.BaseFragmentWithBinding
import com.example.crud.databinding.FragmentLoginBinding
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.SharedPref
import com.example.crud.utils.Validation
import com.example.crud.utils.showCustomToast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class Login : BaseFragmentWithBinding<FragmentLoginBinding>
    (FragmentLoginBinding::inflate)
{

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
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


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(),gso)


        binding.actionGoogleLogin.setOnClickListener{
            if (CheckNetwork(requireContext()).isNetworkConnected){
                signInGoogle()
            }else{
                Toast(requireContext()).showCustomToast(getString(
                    R.string.please_turn_on_internet_connection),requireActivity())
            }
        }

        binding.tvRegister.setOnClickListener{
            val bundle = Bundle()
            //bundle.putBoolean("from_login",true)
            findNavController().navigate(R.id.signUp,bundle)
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

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->

        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handelResults(task)
        }
    }

    private fun handelResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account: GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(requireContext(),task.exception.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credentials).addOnCompleteListener{
            if (it.isSuccessful){
                account.displayName?.let { it1 ->
                    SharedPref.sharedPrefManger(requireContext(),
                        it1,"name")
                }
                SharedPref.sharedPrefManger(requireContext(),true,"isGoogleLogin")
                Toast.makeText(requireContext(),"Welcome",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.fragmentDashboard)
            }else{
                Toast.makeText(requireContext(),it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun login(email:String,password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    SharedPref.sharedPrefManger(requireContext(),true,"isFromLogin")
                    SharedPref.sharedPrefManger(requireContext(),false,"isGoogleLogin")
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