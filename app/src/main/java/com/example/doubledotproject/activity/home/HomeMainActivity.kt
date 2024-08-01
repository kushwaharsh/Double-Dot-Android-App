package com.example.doubledotproject.activity.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.doubledotproject.R
import com.example.doubledotproject.activity.home.adapters.HomeViewPagerAdapter
import com.example.doubledotproject.databinding.ActivityHomeMainBinding
import com.yarolegovich.slidingrootnav.SlideGravity
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import com.yarolegovich.slidingrootnav.callback.DragListener
import com.yarolegovich.slidingrootnav.callback.DragStateListener

class HomeMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeMainBinding
    private lateinit var viewPager: CustomViewPager
    private var slidingRootNav: SlidingRootNav? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewPager
        viewPager = binding.fragmentContainer
        viewPager.adapter = HomeViewPagerAdapter(supportFragmentManager)
        viewPager.setPagingEnabled(false)

        slidingRootNav = SlidingRootNavBuilder(this)
            .withMenuOpened(false)  // Initial state of the menu
            .withContentClickableWhenMenuOpened(true)
            .withSavedState(savedInstanceState)
            .withDragDistance(200)  // Distance for dragging
            .withRootViewScale(0.8f)  // Scale of the root view
            .withRootViewElevation(10)  // Elevation of the root view
            .withGravity(SlideGravity.RIGHT)  // Direction of the sliding menu
            .addRootTransformation(CustomRootTransformation())
            .withMenuLayout(R.layout.drawer_layout)  // Layout of the menu
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

//    private fun updateBackground(isDrawerOpen: Boolean) {
//        if (isDrawerOpen) {
//            // Drawer is open, set drawable as background
//            binding.root.setBackgroundResource(R.drawable.corner_style_overlay)
//        } else {
//            // Drawer is closed, set image as background
//            binding.root.setBackgroundResource(R.drawable.home_screen_bg)
//        }
//    }
}
