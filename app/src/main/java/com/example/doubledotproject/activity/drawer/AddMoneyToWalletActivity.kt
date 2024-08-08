package com.example.doubledotproject.activity.drawer

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.doubledotproject.databinding.ActivityAddMoneyToWalletBinding
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.HomeViewModel

class AddMoneyToWalletActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMoneyToWalletBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMoneyToWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listener()
        observer()
    }

    private fun observer() {
        viewModel.addAmountToWallet.observe(this) {
            when (it) {
                Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(this)
                }

                is Resource.Success -> {
                    ProgressBarUtils.hideProgressDialog()
                    if (it.value?.code == KeyConstants.SUCCESS) {
                        finish()
                    }
                }

                is Resource.Failure -> {
                    ProgressBarUtils.hideProgressDialog()
                    Toast.makeText(this, "Opps! Something Went Wrong", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    ProgressBarUtils.hideProgressDialog()
                    Toast.makeText(this, "Error while fetching data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun listener() {

        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.recharge250.setOnClickListener {
            binding.rechargeAmountTV.setText("250")
        }
        binding.recharge500.setOnClickListener {
            binding.rechargeAmountTV.setText("500")
        }
        binding.recharge1000.setOnClickListener {
            binding.rechargeAmountTV.setText("1000")
        }
        binding.recharge1500.setOnClickListener {
            binding.rechargeAmountTV.setText("1500")
        }

        binding.proceedRechargeBtn.setOnClickListener {
            var body = binding.rechargeAmountTV.text.toString()
            if (body.isEmpty()) {
                Toast.makeText(this, "Please select an amount", Toast.LENGTH_SHORT).show()
            } else {
                var dataMap: HashMap<String, Int> = HashMap()
                dataMap["amount"] = body.toInt()
                viewModel.addAmountToWallet(App.app.prefManager.logginUserData.jwtToken, dataMap)
                Log.e("walletAmount", body.toString())
            }
        }
    }
}