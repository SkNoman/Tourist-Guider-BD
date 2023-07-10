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
import com.example.crud.databinding.FragmentSingUpBinding
import com.example.crud.model.Users
import com.example.crud.utils.CheckNetwork
import com.example.crud.utils.SharedPref
import com.example.crud.utils.Validation
import com.example.crud.utils.showCustomToast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit


class SignUp : BaseFragmentWithBinding<FragmentSingUpBinding>(
    FragmentSingUpBinding::inflate
)
{
    private var TAG = "SignUP"
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().reference



        binding.btnSignUp.setOnClickListener{

            if (CheckNetwork(requireContext()).isNetworkConnected){
                if (validateSignUp() == "OK"){
                    binding.progressBar.visibility = View.VISIBLE;
                    var phone = binding.etPhone.text.toString().trim()
                    phone = "+88$phone"
                    verifyPhoneNumber(phone)
                }else{
                    Toast.makeText(requireContext(),validateSignUp(), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast(requireContext()).showCustomToast(getString(
                    R.string.please_turn_on_internet_connection),requireActivity())
            }
        }

        binding.tvLoginBack.setOnClickListener{
            findNavController().navigate(R.id.login)
        }
    }

    private fun verifyPhoneNumber(phone:String) {

                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(phone) // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(requireActivity()) // Activity (for callback binding)
                    .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)
            binding.progressBar.visibility = View.GONE

            when (e) {
                is FirebaseAuthInvalidCredentialsException -> {
                    Log.e(TAG,e.toString())
                    Toast.makeText(requireContext(),"Invalid Credentials",Toast.LENGTH_SHORT).show()
                }

                is FirebaseTooManyRequestsException -> {
                    Log.e(TAG,e.toString())
                    Toast.makeText(requireContext(),"This device is blocked due to unusual activity\n" +
                            "Please try after sometime!",Toast.LENGTH_SHORT).show()
                }

                is FirebaseAuthMissingActivityForRecaptchaException -> {
                    // reCAPTCHA verification attempted with null Activity
                    Log.e(TAG,e.toString())
                    Toast.makeText(requireContext(),"Something went wrong!",Toast.LENGTH_SHORT).show()
                }
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:$verificationId")
            val bundle = Bundle()
            bundle.putString("OTP",verificationId)
            bundle.putString("resendToken", token.toString())
            bundle.putString("name",binding.etFullName.text.toString())
            bundle.putString("phone",binding.etPhone.text.toString())
            bundle.putString("email",binding.etEmailSignUp.text.toString())
            bundle.putString("pass",binding.etPasswordSignUp.text.toString())
            binding.progressBar.visibility = View.GONE
            findNavController().navigate(R.id.validateOTP,bundle)

        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    signUp()
                } else {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),getString(R.string.something_went_wrong),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun signUp() {
       val name =  binding.etFullName.text.toString()
       val phone =binding.etPhone.text.toString()
       val email =  binding.etEmailSignUp.text.toString()
       val password = binding.etPasswordSignUp.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,phone,email,auth.currentUser?.uid!!)
                    SharedPref.sharedPrefManger(requireContext(),true,"isFromLogin")
                    SharedPref.sharedPrefManger(requireContext(),false,"isGoogleLogin")
                    SharedPref.sharedPrefManger(requireContext(),"","name")
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
            Users(name,phone,email,uid))
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
        }else if(!Validation.emailValidation(binding.etEmailSignUp.text.toString())){
            return "Please enter valid email format"
        }else if(binding.etPasswordSignUp.text.isNullOrEmpty()){
            return "Please enter password"
        }else if (binding.etPasswordSignUp.text!!.length < 6){
            return "Password minimum length is 6"
        }
        return "OK"
    }
}