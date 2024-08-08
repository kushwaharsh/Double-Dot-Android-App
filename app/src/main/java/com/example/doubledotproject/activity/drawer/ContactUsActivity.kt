package com.example.doubledotproject.activity.drawer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.doubledotproject.R
import com.example.doubledotproject.databinding.ActivityContactUsBinding
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.HomeViewModel

class ContactUsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityContactUsBinding
    private val viewModel : HomeViewModel by viewModels()
    private lateinit var phoneNumber : String
    private lateinit var emailAddress : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        observer()
        listener()

    }

    private fun observer() {
        viewModel.staticContentData.observe(this){
            when(it){

                Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(this)
                }
                is Resource.Success -> {
                    ProgressBarUtils.hideProgressDialog()
                    if (it.value?.code == KeyConstants.SUCCESS){
                        phoneNumber = it.value.data.phoneNumber
                        emailAddress = it.value.data.email

                    }
                    else{
                        Toast.makeText(this , "Opps! Something Went Wrong" , Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(this , "Error While Fetching Data" , Toast.LENGTH_SHORT).show()

                }
                else -> {}
            }
        }
    }
    private fun listener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.contactViaCall.setOnClickListener {
            openContactsApp(phoneNumber)
        }
        binding.contactViaMail.setOnClickListener {
            openEmailApp(emailAddress)
        }
        binding.linkedinBtn.setOnClickListener {
            openSocialMediaApp("https://www.linkedin.com/in/harsh-raj-kushwaha-2000111ab/")
        }
        binding.githubBtn.setOnClickListener {
            openSocialMediaApp(" https://github.com/kushwaharsh")
        }
        binding.instagramBtn.setOnClickListener {
            openSocialMediaApp("https://www.linkedin.com/in/harsh-raj-kushwaha-2000111ab/")
        }
        binding.websiteBtn.setOnClickListener {
            openSocialMediaApp("https://kushwaharsh.000webhostapp.com/")
        }
    }

    private fun initView() {
        viewModel.staticContentDetails("CONTACT_US")
    }

    private fun openContactsApp(phoneNumber: String?) {
        phoneNumber?.let {
            // Remove spaces and any other non-numeric characters
            val cleanedPhoneNumber = it.replace("\\s+".toRegex(), "")
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$cleanedPhoneNumber")
            }
            startActivity(intent)
        } ?: run {
            Toast.makeText(this, "Phone number is not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openEmailApp(emailAddress: String?) {
        emailAddress?.let {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$it")
            }
            startActivity(intent)
        } ?: run {
            Toast.makeText(this, "Email address is not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openSocialMediaApp(url: String?) {
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(intent)
        } ?: run {
            Toast.makeText(this, "URL is not available", Toast.LENGTH_SHORT).show()
        }
    }



}