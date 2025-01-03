package com.example.foodalp

import android.app.Application

class FoodApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContainer.initialize(applicationContext)
    }
}
