package com.example.util

import androidx.annotation.DrawableRes
import com.example.ui.R

interface PermissionDialogContent {
    fun getDescription():String
    fun getSettingGuidanceDescription():String
    fun getIcon(): Int
}

class LocationDialogContent : PermissionDialogContent{

    override fun getDescription(): String {
        return "Allow location to see nearby matches."
    }

    override fun getSettingGuidanceDescription(): String {
        return "Please go to Settings -> Permission -> " +
                "To allow location permission."
    }


    @DrawableRes
    override fun getIcon(): Int {
        return R.drawable.location
    }
}