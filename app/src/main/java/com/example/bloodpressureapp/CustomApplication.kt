package com.example.bloodpressureapp

import android.app.Application
import com.example.bloodpressureapp.common.share_preference.AppSharePreference
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CustomApplication(): Application() {
    override fun onCreate() {
        super.onCreate()
        AppSharePreference.getInstance(applicationContext)
    }
}