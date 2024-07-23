package com.example.doubledotproject.activity.home

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class CustomViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {
    private var isPagingEnabled: Boolean = true

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (isPagingEnabled) {
            super.onTouchEvent(event)
        } else {
            false
        }
    }
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (isPagingEnabled) {
            super.onInterceptTouchEvent(event)
        } else {
            false
        }
    }
    fun setPagingEnabled(enabled: Boolean) {
        isPagingEnabled = enabled
    }
}
