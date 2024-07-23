package com.example.doubledotproject.activity.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.doubledotproject.databinding.ActivitySignInBinding
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.Enums
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.AuthViewModel
import com.google.gson.Gson

class SignInActivity : AppCompatActivity() {
    private lateinit var mobileNo : String
    private lateinit var countryCode : String
    private lateinit var binding: ActivitySignInBinding
    private val viewModel : AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initview()
        listener()
        observer()

    }

    private fun observer(){
        viewModel.userloginInWithMob.observe(this){
            when(it){
                is Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(this)
                }
                is Resource.Success -> {
                    if (it.value?.code == KeyConstants.SUCCESS){
                        ProgressBarUtils.hideProgressDialog()

                        val completeDataJson = Gson().toJson(it.value)
                        startActivity(
                            Intent(this,SignInMobOTPActivity::class.java)
                                .putExtra(Enums.CompleteResponseData.toString() , completeDataJson)
                                .putExtra(Enums.MobileNo.toString() , mobileNo)
                                .putExtra(Enums.CountryCode.toString() , countryCode)
                        )
                    }
                }
                is Resource.Failure -> {
                    ProgressBarUtils.hideProgressDialog()
                    Toast.makeText(this , "Opp's Something Gone Wrong" , Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun listener(){
        binding.continueLoginBtn.setOnClickListener {
            mobileNo = binding.mobNoLogin.text.toString().trim()
            countryCode = binding.countryCode.textView_selectedCountry.text.toString().trim()

            if (TextUtils.isEmpty(mobileNo)){
                Toast.makeText(this , "Please Enter Mobile Number to Continue" , Toast.LENGTH_SHORT).show()
            }
            else{
                val params = HashMap<String , String>()
                params["phoneNumber"] = mobileNo
                viewModel.loginInWithMob(params)
            }
        }

        binding.backBtnLogin.setOnClickListener {
            finish()
        }
    }

    private fun initview(){}
}