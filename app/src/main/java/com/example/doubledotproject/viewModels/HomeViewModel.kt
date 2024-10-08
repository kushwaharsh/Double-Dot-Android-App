package com.example.doubledotproject.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubledotproject.apiResponse.AddAmonuntToWalletResponseModel
import com.example.doubledotproject.apiResponse.AuthResponseModel
import com.example.doubledotproject.apiResponse.EditUserProfileResponseModel
import com.example.doubledotproject.apiResponse.ExpertDetailsResponse
import com.example.doubledotproject.apiResponse.GetExpertListResponse
import com.example.doubledotproject.apiResponse.GetWalletAmount
import com.example.doubledotproject.apiResponse.OTPResponse
import com.example.doubledotproject.apiResponse.ProfileDetailsResponseModel
import com.example.doubledotproject.apiResponse.StaticContentResponseModel
import com.example.doubledotproject.apiResponse.WalletTransactionResponseModel
import com.example.doubledotproject.repository.HomeRepository
import com.example.doubledotproject.utiles.Resource
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _getExpertListData: MutableLiveData<Resource<GetExpertListResponse?>?> =
        MutableLiveData()
    val getExpertListData: LiveData<Resource<GetExpertListResponse?>?>
        get() = _getExpertListData

    fun getExpertData(token: String) {
        viewModelScope.launch {
            _getExpertListData.value = Resource.Loading
            _getExpertListData.value = HomeRepository.getExpertList(token)
        }
    }

    private val _getExpertDetailsData: MutableLiveData<Resource<ExpertDetailsResponse?>?> =
        MutableLiveData()
    val getExpertDetailsData: LiveData<Resource<ExpertDetailsResponse?>?>
        get() = _getExpertDetailsData

    fun getExpertDetails(token: String, expertId: String) {
        viewModelScope.launch {
            _getExpertDetailsData.value = Resource.Loading
            _getExpertDetailsData.value = HomeRepository.getExpertData(token, expertId)
        }
    }

    private val _getWalletDetailsData: MutableLiveData<Resource<WalletTransactionResponseModel?>?> =
        MutableLiveData()
    val getWalletDetailsData: LiveData<Resource<WalletTransactionResponseModel?>?>
        get() = _getWalletDetailsData

    fun getWalletTransactionDetails(token: String) {
        viewModelScope.launch {
            _getWalletDetailsData.value = Resource.Loading
            _getWalletDetailsData.value = HomeRepository.getWalletTransactionDetails(token)
        }
    }

    private val _addAmountToWallet: MutableLiveData<Resource<AddAmonuntToWalletResponseModel?>?> =
        MutableLiveData()
    val addAmountToWallet: LiveData<Resource<AddAmonuntToWalletResponseModel?>?>
        get() = _addAmountToWallet

    fun addAmountToWallet(token: String, body: HashMap<String, Int>) {
        viewModelScope.launch {
            _addAmountToWallet.value = Resource.Loading
            _addAmountToWallet.value = HomeRepository.addAmountToWallet(token, body)
        }
    }

    private val _getWalletTotalAmount: MutableLiveData<Resource<GetWalletAmount?>?> =
        MutableLiveData()
    val getWalletTotalAmount: LiveData<Resource<GetWalletAmount?>?>
        get() = _getWalletTotalAmount

    fun getWalletAmount(token: String) {
        viewModelScope.launch {
            _getWalletTotalAmount.value = Resource.Loading
            _getWalletTotalAmount.value = HomeRepository.getWalletAmount(token)
        }
    }

    private val _editedUserProfileData: MutableLiveData<Resource<EditUserProfileResponseModel?>?> =
        MutableLiveData()
    val editedUserProfileData: LiveData<Resource<EditUserProfileResponseModel?>?>
        get() = _editedUserProfileData

    fun editUserProfile(token: String, param: HashMap<String, Any>) {
        viewModelScope.launch {
            _editedUserProfileData.value = Resource.Loading
            _editedUserProfileData.value = HomeRepository.editUserProfile(token, param)
        }
    }

    private val _staticContentData : MutableLiveData<Resource<StaticContentResponseModel?>?> = MutableLiveData()
    val staticContentData : LiveData<Resource<StaticContentResponseModel?>?>
        get() = _staticContentData

    fun staticContentDetails(type : String){
        viewModelScope.launch {
            _staticContentData.value = Resource.Loading
            _staticContentData.value = HomeRepository.staticContentDetails(type)
        }
    }

    private val _deleteAccountUser : MutableLiveData<Resource<OTPResponse?>?> = MutableLiveData()
    val deleteAccountUser : LiveData<Resource<OTPResponse?>?>
        get() = _deleteAccountUser

    fun deleteUserAccount(token: String){
        viewModelScope.launch {
            _deleteAccountUser.value = Resource.Loading
            _deleteAccountUser.value = HomeRepository.deleteUserAccount(token)
        }
    }

    private val _logoutUserAccount : MutableLiveData<Resource<AuthResponseModel?>?> = MutableLiveData()
    val logoutUserAccount : LiveData<Resource<AuthResponseModel?>?>
        get() = _logoutUserAccount
    fun logoutUser(token: String){
        viewModelScope.launch {
            _logoutUserAccount.value = Resource.Loading
            _logoutUserAccount.value = HomeRepository.logoutUser(token)
        }
    }

    private val _userProfileData : MutableLiveData<Resource<ProfileDetailsResponseModel?>?> = MutableLiveData()
    val userProfileData : LiveData<Resource<ProfileDetailsResponseModel?>?>
        get() = _userProfileData
    fun profileDetails(token: String){
        viewModelScope.launch {
            _userProfileData.value = Resource.Loading
            _userProfileData.value = HomeRepository.profileDetails(token)
        }
    }
}