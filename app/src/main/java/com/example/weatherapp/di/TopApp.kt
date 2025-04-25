package com.example.weatherapp.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Init Hilt for dependency injection
@HiltAndroidApp
class TopApp: Application() {
}