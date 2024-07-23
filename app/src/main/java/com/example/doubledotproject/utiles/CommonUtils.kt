package com.example.doubledotproject.utiles

import android.text.TextUtils
import android.util.Patterns

object CommonUtils {

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}