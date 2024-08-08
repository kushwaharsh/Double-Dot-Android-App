package com.example.doubledotproject.activity.home

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.doubledotproject.R
import com.example.doubledotproject.activity.home.adapters.ExpertDetailsActivityRecyclerAdapter
import com.example.doubledotproject.activity.reusableClasses.BlurDialog
import com.example.doubledotproject.apiResponse.ExpertDetailsData
import com.example.doubledotproject.apiResponse.ExpertRatingDetail
import com.example.doubledotproject.databinding.ActivityExperDetailsBinding
import com.example.doubledotproject.databinding.DialogConnectWithExpertBinding
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.HomeViewModel
import com.google.gson.Gson
import jp.wasabeef.blurry.Blurry

class ExperDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExperDetailsBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: ExpertDetailsActivityRecyclerAdapter
    private var arrayList = ArrayList<ExpertRatingDetail>()
    private var expertDetailData: ExpertDetailsData? = null


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
                    val data = resource.value
                    if (data?.code == KeyConstants.SUCCESS) {
                        ProgressBarUtils.hideProgressDialog()
                        binding.mainLinearLayout.visibility = View.VISIBLE
                        data.data.let { setData(it) }
                        data.ratingDetails.let {
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
        binding.connectBtn.setOnClickListener {
            showCustomDialog()
        }

    }

    private fun initView() {
        val clickedExpertId = intent.getStringExtra("ClickedExpertId")
        viewModel.getExpertDetails(
            App.app.prefManager.logginUserData.jwtToken,
            clickedExpertId.orEmpty() // Use .orEmpty() to provide a default value
        )

        binding.recyclerViewHorizontal.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = ExpertDetailsActivityRecyclerAdapter(arrayList, this)
        binding.recyclerViewHorizontal.adapter = adapter
    }

    private fun setData(data: List<ExpertDetailsData>) {
        if (data.isNotEmpty()) {
            val expertData = data[0]
            expertDetailData = expertData
            binding.expertNameTV.text = expertData.fullName
            binding.expertExpertiseFeild.text = expertData.keywordExpertise.joinToString(", ")
            binding.expertDescription.text = expertData.description
            binding.expertLanguageTV.text = expertData.language
            binding.expertDiscountedConsultationFee.text =
                "${expertData.discountedConsultationFee}/"
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

    private fun showCustomDialog() {
        val dialogBinding = DialogConnectWithExpertBinding.inflate(layoutInflater)
        val connectDialog = BlurDialog(this, R.style.TransparentDialogTheme)
        connectDialog.setContentView(dialogBinding.root)

        dialogBinding.crossBtn.setOnClickListener {
            connectDialog.dismiss()
        }
        dialogBinding.audioCallBtn.setOnClickListener { }
        dialogBinding.videoCallBtn.setOnClickListener { }
        dialogBinding.chatBtn.setOnClickListener { }

        expertDetailData?.let {
            dialogBinding.expertName.text = it.fullName
            dialogBinding.expertExpertiseFeild.text = it.keywordExpertise.joinToString(",")
            dialogBinding.expertRating.text = String.format("%.1f", it.rating.toFloat())
        }
        Log.e("responseData", Gson().toJson(expertDetailData))

        val defaultColor = Color.parseColor("#DDDDDD") // Light gray color for default
        val activeColorAudio = Color.parseColor("#37CF29")
        val activeColorVideoandChat = Color.parseColor("#7C7499")

        dialogBinding.audioCallBtn.backgroundTintList = ColorStateList.valueOf(defaultColor)
        dialogBinding.videoCallBtn.backgroundTintList = ColorStateList.valueOf(defaultColor)
        dialogBinding.chatBtn.setBackgroundResource(R.drawable.grey_border_style)


        expertDetailData?.availability?.forEach { option ->
            when (option) {
                "call" -> dialogBinding.audioCallBtn.backgroundTintList =
                    ColorStateList.valueOf(activeColorAudio)

                "chat" -> dialogBinding.videoCallBtn.backgroundTintList =
                    ColorStateList.valueOf(activeColorVideoandChat)

                "video" -> {
                    dialogBinding.chatBtn.setBackgroundResource(R.drawable.dialog_button_border_style)
                    dialogBinding.chatActiveIcon.visibility = View.VISIBLE
                    dialogBinding.chatActiveText.visibility = View.VISIBLE
                    dialogBinding.chatDefaultIcon.visibility = View.GONE
                    dialogBinding.chatDefaultText.visibility = View.GONE
                }
            }
        }

        connectDialog.show()
    }

    override fun onResume() {
        super.onResume()
        val walletData = App.app.prefManager.WalletCurrentData

        val displayAmount = walletData?.toDoubleOrNull()?.let { amount ->
            String.format("₹ %.1f", amount)
        } ?: "₹ 0.0"

        binding.walletBalanceTV.text = displayAmount

    }

}
