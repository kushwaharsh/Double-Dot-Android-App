package com.example.doubledotproject.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubledotproject.apiResponse.ExpertDetailsResponse
import com.example.doubledotproject.apiResponse.GetExpertListResponse
import com.example.doubledotproject.repository.HomeRepository
import com.example.doubledotproject.utiles.Resource
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _getExpertListData: MutableLiveData<Resource<GetExpertListResponse?>?> = MutableLiveData()
    val getExpertListData: LiveData<Resource<GetExpertListResponse?>?>
        get() = _getExpertListData
    fun getExpertData(token: String) {
        viewModelScope.launch {
            _getExpertListData.value = Resource.Loading
            _getExpertListData.value = HomeRepository.getExpertList(token)
        }
    }

    private val _getExpertDetailsData : MutableLiveData<Resource<ExpertDetailsResponse?>?> = MutableLiveData()
    val getExpertDetailsData :LiveData<Resource<ExpertDetailsResponse?>?>
        get() = _getExpertDetailsData
    fun getExpertDetails(token: String , expertId : String){
        viewModelScope.launch {
            _getExpertDetailsData.value = Resource.Loading
            _getExpertDetailsData.value = HomeRepository.getExpertData(token , expertId)
        }
    }
}