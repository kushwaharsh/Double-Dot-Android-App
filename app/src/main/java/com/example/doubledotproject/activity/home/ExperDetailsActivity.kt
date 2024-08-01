package com.example.doubledotproject.activity.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.doubledotproject.activity.home.adapters.ExpertDetailsActivityRecyclerAdapter
import com.example.doubledotproject.apiResponse.ExpertDetailsData
import com.example.doubledotproject.apiResponse.ExpertRatingDetail
import com.example.doubledotproject.databinding.ActivityExperDetailsBinding
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.HomeViewModel

class ExperDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExperDetailsBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: ExpertDetailsActivityRecyclerAdapter
    private var arrayList = ArrayList<ExpertRatingDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExperDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        observer()
        listener()
    }

    private fun observer() {
        viewModel.getExpertDetailsData.observe(this) { resource ->
            when (resource) {
                Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(this)
                }
                is Resource.Success -> {
                    ProgressBarUtils.hideProgressDialog()
                    val data = resource.value
                    if (data?.code == KeyConstants.SUCCESS) {
                        binding.mainLinearLayout.visibility = View.VISIBLE
                        data.data?.let { setData(it) }
                        data.ratingDetails?.let {
                            arrayList = it as ArrayList<ExpertRatingDetail>
                            adapter.setNewList(arrayList)
                        }
                    } else {
                        // Handle case where code is not success
                        Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Failure -> {
                    ProgressBarUtils.hideProgressDialog()
                    Toast.makeText(this, "Oops! Something went wrong", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun listener() {
        binding.backtoHomeFragBtn.setOnClickListener {
            finish()
        }

    }

    private fun initView() {
        val clickedExpertId = intent.getStringExtra("ClickedExpertId")
        viewModel.getExpertDetails(
            App.app.prefManager.logginUserData.jwtToken,
            clickedExpertId.orEmpty() // Use .orEmpty() to provide a default value
        )

        binding.recyclerViewHorizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = ExpertDetailsActivityRecyclerAdapter(arrayList , this)
        binding.recyclerViewHorizontal.adapter = adapter
    }

    private fun setData(data: List<ExpertDetailsData>) {
        if (data.isNotEmpty()) {
            val expertData = data[0]
            binding.expertNameTV.text = expertData.fullName
            binding.expertExpertiseFeild.text = expertData.keywordExpertise.joinToString(", ")
            binding.expertDescription.text = expertData.description
            binding.expertLanguageTV.text = expertData.language
            binding.expertDiscountedConsultationFee.text = "${expertData.discountedConsultationFee}/"
            binding.expertConsultationFee.text = "${expertData.consultationFee}/"
            binding.expertRating.text = String.format("%.1f", expertData.rating.toFloat())

            Glide.with(this)
                .load(expertData.image)
                .into(binding.expertImg)
        } else {
            binding.mainLinearLayout.visibility = View.GONE
            Toast.makeText(this, "No expert details available", Toast.LENGTH_SHORT).show()
        }
    }
}
