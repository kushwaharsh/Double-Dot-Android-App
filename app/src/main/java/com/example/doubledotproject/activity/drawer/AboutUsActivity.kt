package com.example.doubledotproject.activity.drawer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.doubledotproject.R
import com.example.doubledotproject.databinding.ActivityAboutUsBinding
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.HomeViewModel

class AboutUsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAboutUsBinding
    private val viewModel : HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
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
                        binding.titleTV.text = it.value.data.title
                        binding.contentTV.text = it.value.data.description
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
    }

    private fun initView() {
       viewModel.staticContentDetails("ABOUT_US")
    }
}