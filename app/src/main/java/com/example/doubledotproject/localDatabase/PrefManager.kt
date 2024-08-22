package com.example.doubledotproject.localDatabase

import android.content.Context
import com.example.doubledotproject.apiResponse.CurrentWalletAmount
import com.example.doubledotproject.apiResponse.LoginData
import com.example.doubledotproject.utiles.Enums
import com.example.doubledotproject.utiles.KeyConstants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefManager private constructor(private val context: Context){
    private val sharedPref = context.getSharedPreferences(KeyConstants.PREF_NAME , Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    var accessToken : String
        get() = sharedPref.getString("accessToken" , "") ?: ""
        set(value) {
            editor.putString("accessToken" , value).apply()
        }

    var isLoggedIn : Boolean
        get() = sharedPref.getBoolean(Enums.IsLoggedIn.toString() , false)
        set(value) {
            editor.putBoolean(Enums.IsLoggedIn.toString() , value).apply()
        }

    var logginUserData : LoginData
        get() {
            val json = sharedPref.getString(Enums.LoginUserData.toString(), null)
            val type = object : TypeToken<LoginData?>() {}.type
            return Gson().fromJson(json, type)
        }
        set(value) {
            val json = Gson().toJson(value)
            editor.putString(Enums.LoginUserData.toString(), json).apply()
        }


    var WalletCurrentData: String
        get() {
            val json = sharedPref.getString(Enums.WalletCurrentData.toString(), null)
            return json ?: "0.0"
        }
        set(value) {
            editor.putString(Enums.WalletCurrentData.toString(), value).apply()
        }


    fun clearPreferences() {
        editor.clear().apply()
    }
    companion object {
        fun get(context: Context) = PrefManager(context)
    }
}
