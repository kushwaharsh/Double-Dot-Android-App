package com.example.doubledotproject.activity.home.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.doubledotproject.activity.home.HistoryFragment
import com.example.doubledotproject.activity.home.HomeFragment
import com.example.doubledotproject.activity.home.ProfileFragment

class HomeViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> HistoryFragment()
            2 -> ProfileFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }

    override fun getCount(): Int = 3
}
