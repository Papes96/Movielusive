package com.example.movielusive

import android.app.Application
import com.google.android.material.color.DynamicColors

class MovielusiveApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}