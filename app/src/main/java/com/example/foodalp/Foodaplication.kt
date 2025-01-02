package com.example.foodalp

import android.app.Application

class FoodApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize the AppContainer with the context
        AppContainer.initialize(applicationContext)
    }
}
