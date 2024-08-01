package com.example.doubledotproject.activity.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.doubledotproject.R
import com.example.doubledotproject.activity.auth.SignInActivity
import com.example.doubledotproject.activity.home.HomeMainActivity
import com.example.doubledotproject.utiles.App

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler(Looper.getMainLooper()).postDelayed({
            if (App.app.prefManager.isLoggedIn){
                startActivity(Intent(this, HomeMainActivity::class.java))
            } else {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 2000)
    }
}