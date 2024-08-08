package com.example.doubledotproject.activity.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.doubledotproject.R
import com.example.doubledotproject.apiResponse.Data
import com.example.doubledotproject.databinding.FragmentHistoryBinding
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.HomeViewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel : HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
       binding = FragmentHistoryBinding.inflate(inflater , container , false)

        initView()
        observer()
        listener()

        return binding.root
    }

    private fun observer() {
        //Observer for wallet amount
        viewModel.getWalletTotalAmount.observe(viewLifecycleOwner){
            when(it){
                Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(requireContext())
                }
                is Resource.Success -> {
                    ProgressBarUtils.hideProgressDialog()
                    if (it.value?.code == KeyConstants.SUCCESS){

                        it.value.data?.currentAmount?.amount?.let { amount ->
                            val currentWalletAmount = amount.toString()
                            binding.walletBalanceTV.text = "₹${currentWalletAmount}"
                            App.app.prefManager.WalletCurrentData = currentWalletAmount
                        } ?: run {
                            // Handle the case where amount is null
                            binding.walletBalanceTV.text = "₹ 0.0"
                            App.app.prefManager.WalletCurrentData = "0.0"
                        }

                    }
                }
                is Resource.Failure -> {
                    ProgressBarUtils.hideProgressDialog()
                    Toast.makeText(requireContext() , "Opps! Something Went Wrong" , Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext() , "Erreo while fetching data" , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun listener() {

    }

    private fun initView() {
        binding.userNameTV.text = App.app.prefManager.logginUserData.fullName

        if (App.app.prefManager.logginUserData.walletBalance == ""){
            binding.walletBalanceTV.text = "₹ ${0}"
        }else {
            binding.walletBalanceTV.text = " ₹ ${App.app.prefManager.logginUserData.walletBalance}"

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWalletAmount(App.app.prefManager.logginUserData.jwtToken)
    }
}