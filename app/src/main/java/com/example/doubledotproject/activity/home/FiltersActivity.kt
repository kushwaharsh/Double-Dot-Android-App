package com.example.doubledotproject.activity.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.doubledotproject.R
import com.example.doubledotproject.databinding.ActivityFiltersBinding

class FiltersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFiltersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiltersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listener()
    }

    private fun listener() {
        binding.filtersBackBtn.setOnClickListener {
            finish()
        }

    }
}