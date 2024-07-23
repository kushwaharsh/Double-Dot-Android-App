package com.example.doubledotproject.activity.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.doubledotproject.activity.home.HomeMainActivity
import com.example.doubledotproject.databinding.ActivityAuthSuccessBinding

class AuthSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listener()
    }

    private fun listener(){
        binding.goToHomeBtn.setOnClickListener {
        startActivity(Intent(this@AuthSuccessActivity,HomeMainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or  Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
        }

        binding.crossBtn.setOnClickListener {
            startActivity(Intent(this@AuthSuccessActivity,HomeMainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or  Intent.FLAG_ACTIVITY_NEW_TASK))
            finish()
        }
    }
}
