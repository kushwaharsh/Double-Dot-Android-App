package com.example.doubledotproject.activity.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.doubledotproject.activity.home.adapters.HomeViewPagerAdapter
import com.example.doubledotproject.databinding.ActivityHomeBinding

class HomeMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewPager
        viewPager = binding.fragmentContainer
        viewPager.adapter = HomeViewPagerAdapter(supportFragmentManager)
        viewPager.setPagingEnabled(false)

        listener()
    }

    private fun listener() {
        binding.homeIcon.setOnClickListener {
            viewPager.currentItem = 0
            binding.homeIconWithTitle.customHomeIcon.visibility = View.VISIBLE
            binding.profileIconWithTitle.customProfileIcon.visibility = View.GONE
            binding.historyIconWithTitle.customHistoryIcon.visibility = View.GONE
            binding.homeIcon.visibility = View.GONE
            binding.historyIcon.visibility = View.VISIBLE
            binding.profileIcon.visibility = View.VISIBLE
        }
        binding.historyIcon.setOnClickListener {
            viewPager.currentItem = 1
            binding.historyIconWithTitle.customHistoryIcon.visibility = View.VISIBLE
            binding.homeIconWithTitle.customHomeIcon.visibility = View.GONE
            binding.profileIconWithTitle.customProfileIcon.visibility = View.GONE
            binding.historyIcon.visibility = View.GONE
            binding.homeIcon.visibility = View.VISIBLE
            binding.profileIcon.visibility = View.VISIBLE
        }
        binding.profileIcon.setOnClickListener {
            viewPager.currentItem = 2
            binding.profileIconWithTitle.customProfileIcon.visibility = View.VISIBLE
            binding.homeIconWithTitle.customHomeIcon.visibility = View.GONE
            binding.historyIconWithTitle.customHistoryIcon.visibility = View.GONE
            binding.profileIcon.visibility = View.GONE
            binding.homeIcon.visibility = View.VISIBLE
            binding.historyIcon.visibility = View.VISIBLE
        }

        viewPager.currentItem = 0

    }

}
