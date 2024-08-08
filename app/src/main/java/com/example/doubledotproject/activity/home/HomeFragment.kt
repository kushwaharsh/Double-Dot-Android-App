package com.example.doubledotproject.activity.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doubledotproject.R
import com.example.doubledotproject.activity.drawer.WalletActivity
import com.example.doubledotproject.activity.home.adapters.HomeRecyclerViewAdapter
import com.example.doubledotproject.apiResponse.Data
import com.example.doubledotproject.apiResponse.GetExpertListResponse
import com.example.doubledotproject.databinding.FragmentHomeBinding
import com.example.doubledotproject.utiles.App
import com.example.doubledotproject.utiles.Enums
import com.example.doubledotproject.utiles.KeyConstants
import com.example.doubledotproject.utiles.ProgressBarUtils
import com.example.doubledotproject.utiles.Resource
import com.example.doubledotproject.viewModels.HomeViewModel
import com.google.gson.Gson

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeRecyclerViewAdapter
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerView: RecyclerView
    var arrayList = ArrayList<Data>()
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        Log.e("token", App.app.prefManager.logginUserData.jwtToken)
        initview()
        observer()
        listener()

        return binding.root

    }

    private fun observer() {
        viewModel.getExpertListData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    ProgressBarUtils.showProgressDialog(requireContext())
                }

                is Resource.Success -> {
                    if (it.value?.code == KeyConstants.SUCCESS) {
                        ProgressBarUtils.hideProgressDialog()

                        arrayList = it.value.data as ArrayList<Data>
                        adapter.setNewList(arrayList)
                    } else {
                        ProgressBarUtils.hideProgressDialog()
                        Toast.makeText(
                            requireContext(), "Error While Fetching Data", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resource.Failure -> {
                    ProgressBarUtils.hideProgressDialog()
                    Toast.makeText(
                        requireContext(), "Opps! Something Went Wrong", Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    Toast.makeText(
                        requireContext(), "Error While fetching Data", Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }

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

        binding.filtersBtn.setOnClickListener {
            startActivity(Intent(requireContext(), FiltersActivity::class.java))
        }
        binding.walletBalanceTV.setOnClickListener {
            startActivity(Intent(requireContext() , WalletActivity::class.java))
        }

    }

    private fun initview() {
        binding.homeFragmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = HomeRecyclerViewAdapter(arrayList) { position ->
            val expertData = arrayList[position]
            val clickedExpertId = expertData._id
            val intent = Intent(requireContext(), ExperDetailsActivity::class.java).apply {
                putExtra("ClickedExpertId", clickedExpertId)
            }
            startActivity(intent)
        }// Initialize with empty list
        binding.homeFragmentRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        viewModel.getExpertData(App.app.prefManager.logginUserData.jwtToken)

        binding.userNameTV.text = App.app.prefManager.logginUserData.fullName
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWalletAmount(App.app.prefManager.logginUserData.jwtToken)
    }
}
