package com.example.doubledotproject.activity.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.doubledotproject.R
import com.example.doubledotproject.activity.home.adapters.HomeViewPagerAdapter
import com.example.doubledotproject.databinding.ActivityHomeBinding
import com.yarolegovich.slidingrootnav.SlideGravity
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import com.yarolegovich.slidingrootnav.callback.DragListener

class HomeMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPager: CustomViewPager
    private var slidingRootNav: SlidingRootNav? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewPager
        viewPager = binding.fragmentContainer
        viewPager.adapter = HomeViewPagerAdapter(supportFragmentManager)
        viewPager.setPagingEnabled(false)

        slidingRootNav = SlidingRootNavBuilder(this)
            .withMenuOpened(false)
            .withContentClickableWhenMenuOpened(true)
            .withSavedState(savedInstanceState)
            //.withRootViewElevation(7)
            .withDragDistance(200)
            .withRootViewScale(0.8f)
            .withRootViewElevation(10)
            .withGravity(SlideGravity.RIGHT)
            .withMenuLayout(R.layout.drawer_layout)
            .addDragListener(object : DragListener {
                override fun onDrag(progress: Float) {
                    // Detect when drawer is closed
                    if (progress == 0f) {
                        updateIcons(0)
                        viewPager.currentItem = 0
                    }
                }
            })
            .inject()

        setupListeners()
        updateIcons(0)
    }

    private fun setupListeners() {
        binding.homeIcon.setOnClickListener {
            updateIcons(0)
            viewPager.currentItem = 0
        }
        binding.historyIcon.setOnClickListener {
            updateIcons(1)
            viewPager.currentItem = 1
        }
        binding.profileIcon.setOnClickListener {
            updateIcons(2)
            slidingRootNav?.openMenu()
        }
    }

    private fun updateIcons(selectedTab: Int) {
        when (selectedTab) {
            0 -> {
                binding.homeIconWithTitle.customHomeIcon.visibility = View.VISIBLE
                binding.historyIconWithTitle.customHistoryIcon.visibility = View.GONE
                binding.profileIconWithTitle.customProfileIcon.visibility = View.GONE
                binding.homeIcon.visibility = View.GONE
                binding.historyIcon.visibility = View.VISIBLE
                binding.profileIcon.visibility = View.VISIBLE
            }
            1 -> {
                binding.historyIconWithTitle.customHistoryIcon.visibility = View.VISIBLE
                binding.homeIconWithTitle.customHomeIcon.visibility = View.GONE
                binding.profileIconWithTitle.customProfileIcon.visibility = View.GONE
                binding.historyIcon.visibility = View.GONE
                binding.homeIcon.visibility = View.VISIBLE
                binding.profileIcon.visibility = View.VISIBLE
            }
            2 -> {
                binding.profileIconWithTitle.customProfileIcon.visibility = View.VISIBLE
                binding.homeIconWithTitle.customHomeIcon.visibility = View.GONE
                binding.historyIconWithTitle.customHistoryIcon.visibility = View.GONE
                binding.profileIcon.visibility = View.GONE
                binding.homeIcon.visibility = View.VISIBLE
                binding.historyIcon.visibility = View.VISIBLE
            }
        }
    }
}
