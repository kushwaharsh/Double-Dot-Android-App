package com.example.doubledotproject.activity.drawer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doubledotproject.R
import com.example.doubledotproject.activity.drawer.adapters.WalletTransactionRecyclerAdapter
import com.example.doubledotproject.apiResponse.Data
import com.example.doubledotproject.apiResponse.TransHistory
import com.example.doubledotproject.databinding.ActivityWalletBinding
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.HomeViewModel

class WalletActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWalletBinding
    private lateinit var adapter : WalletTransactionRecyclerAdapter
    private val viewModel : HomeViewModel by viewModels()
    var arrayList = ArrayList<TransHistory>()
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        listener()
        observer()

    }

    private fun observer() {
        viewModel.getWalletDetailsData.observe(this){ it ->
            when(it){

                is Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(this)
                }
                is Resource.Success -> {
                    ProgressBarUtils.hideProgressDialog()
                    if (it.value?.code == KeyConstants.SUCCESS){
                        arrayList.clear()
                        it.value.data?.transHistory?.let {
                            arrayList.addAll(it)
                            adapter.setNewList(arrayList)
                        }
                        // Check if the necessary data is not null
                        it.value.data?.currentAmount?.let { currentAmount ->
                            val amountString = currentAmount.amount?.toString() ?: "0.0"
                            binding.wallerBalanceTV.text = "₹ $amountString"
                            App.app.prefManager.WalletCurrentData = amountString
                        } ?: run {
                            binding.wallerBalanceTV.text = "₹ 0.0" // Default value for TextView
                            App.app.prefManager.WalletCurrentData = "0.0" // Default value for prefManager
                        }

                    }
                }
                is Resource.Failure -> {
                    ProgressBarUtils.hideProgressDialog()
                    Toast.makeText(this , "Opps! Something Went worng" , Toast.LENGTH_SHORT).show()
                }
                else -> {
                    ProgressBarUtils.hideProgressDialog()
                    Toast.makeText(this , "Error while fetching Data" , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun listener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.addMoneyBtn.setOnClickListener {
            startActivity(Intent(this , AddMoneyToWalletActivity::class.java))
        }
    }

    private fun initView() {
        binding.walletRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = WalletTransactionRecyclerAdapter(emptyList() , this)
        binding.walletRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getWalletTransactionDetails(App.app.prefManager.logginUserData.jwtToken)
    }
}