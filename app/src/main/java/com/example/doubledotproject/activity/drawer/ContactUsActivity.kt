package com.example.doubledotproject.activity.drawer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.doubledotproject.R
import com.example.doubledotproject.databinding.ActivityContactUsBinding

class ContactUsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityContactUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listener()

    }

    private fun listener() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.contactViaCall.setOnClickListener {
            Toast.makeText(this , "Calling....Connecting with agent" , Toast.LENGTH_SHORT).show()
        }
        binding.contactViaMail.setOnClickListener {
            Toast.makeText(this , "Write us on Mail..." , Toast.LENGTH_SHORT).show()
        }
        binding.followOnInsta.setOnClickListener {
            Toast.makeText(this , "Followed on Instagram" , Toast.LENGTH_SHORT).show()
        }
        binding.followOnFacebook.setOnClickListener {
            Toast.makeText(this , "Followed on Facebook" , Toast.LENGTH_SHORT).show()
        }
        binding.followOnTelegram.setOnClickListener {
            Toast.makeText(this , "Followed on Telegram" , Toast.LENGTH_SHORT).show()
        }
        binding.followOnWhatsapp.setOnClickListener {
            Toast.makeText(this , "Followed on Whatsapp" , Toast.LENGTH_SHORT).show()
        }
    }
}