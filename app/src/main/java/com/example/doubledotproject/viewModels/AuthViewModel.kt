package com.example.doubledotproject.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubledotproject.apiResponse.AuthResponseModel
import com.example.doubledotproject.apiResponse.LoginData
import com.example.doubledotproject.apiResponse.OTPResponse
import com.example.doubledotproject.repository.AuthRepository
import com.example.doubledotproject.utiles.Resource
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val  _userloginInWithMob : MutableLiveData<Resource<AuthResponseModel?>?> = MutableLiveData()
    val userloginInWithMob : LiveData<Resource<AuthResponseModel?>?>
        get() = _userloginInWithMob
    fun loginInWithMob(params : HashMap<String , String>){
        viewModelScope.launch{
        _userloginInWithMob.value = Resource.Loading
        _userloginInWithMob.value = AuthRepository.loginInWithMob(params)}
    }

    private val _userResendMobOtp : MutableLiveData<Resource<OTPResponse?>?> = MutableLiveData()
    val userResendMobOtp : LiveData<Resource<OTPResponse?>?>
        get() = _userResendMobOtp
    fun resendMobOtp(phoneNumber:Long){
        viewModelScope.launch {
            _userResendMobOtp.value = Resource.Loading
            _userResendMobOtp.value = AuthRepository.resendMobOtp(phoneNumber)
        }
    }

    private val _userSignUp : MutableLiveData<Resource<AuthResponseModel?>?> = MutableLiveData()
    val userSignUp : LiveData<Resource<AuthResponseModel?>?>
        get() = _userSignUp
    fun signUpUser(params: HashMap<String, String>){
        viewModelScope.launch {
            _userSignUp.value = Resource.Loading
            _userSignUp.value = AuthRepository.signUpUser(params)
        }
    }
}