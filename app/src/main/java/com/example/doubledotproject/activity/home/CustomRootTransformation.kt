package com.example.doubledotproject.activity.home

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.yarolegovich.slidingrootnav.transform.RootTransformation

class CustomRootTransformation : RootTransformation {
    override fun transform(dragProgress: Float, rootView: View) {
        // Custom transformation logic to adjust the shape of the activity/fragment
        val radius = 80 * dragProgress // Example to change corner radius based on drag progress
        rootView.clipToOutline = true
        rootView.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, radius)
            }
        }
    }
}
