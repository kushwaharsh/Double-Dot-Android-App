package com.example.doubledotproject.activity.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.doubledotproject.databinding.ActivitySignUpBinding
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.CommonUtils
import com.example.doubledotproject.utiles.Enums
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.AuthViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel : AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listener()
        observer()

    }

    private fun observer() {
        viewModel.userSignUp.observe(this){
            when(it){
                is Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(this)
                }
                is Resource.Success -> {
                    if (it.value?.code == KeyConstants.SUCCESS){
                        ProgressBarUtils.hideProgressDialog()
                        App.app.prefManager.isLoggedIn = true
                        App.app.prefManager.logginUserData = it.value.data
                        startActivity(Intent(this , AuthSuccessActivity::class.java))
                        finish()

                    }
                }
                is Resource.Failure -> {
                    ProgressBarUtils.hideProgressDialog()
                    Toast.makeText(this , "Opp's Something Went Wrong" , Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun listener() {


        binding.signUpContinueBtn.setOnClickListener {
        val fullName = binding.fullNameET.text.toString()
        val email = binding.emailEt.text.toString()
        val mobileNo = intent.getStringExtra(Enums.MobileNo.toString()).toString()

        if (fullName.isEmpty()){
            Toast.makeText(this , "Enter your Name" , Toast.LENGTH_SHORT).show()
        }else if(!CommonUtils.isValidEmail(email)){
            Toast.makeText(this , "Enter your Name" , Toast.LENGTH_SHORT).show()
        }else{
            val params = HashMap<String , String>()
            params["fullName"] = fullName
            params["email"] = email
            params["phoneNumber"] = mobileNo
            params["gender"] = "Male"
            params["dob"] = "123445"
            params["state"] = "Uttar Pradesh"
            params["city"] = "Noida"
            viewModel.signUpUser(params)
        }
        }

        binding.backToLoginBtn.setOnClickListener {
            startActivity(Intent(this , SignInActivity::class.java))
            finish()
        }

    }


}