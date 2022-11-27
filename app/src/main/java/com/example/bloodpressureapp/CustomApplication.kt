package com.example.bloodpressureapp

import android.app.Application
import com.example.bloodpressureapp.common.share_preference.AppSharePreference
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CustomApplication() : Application() {

    companion object {
        lateinit var app: CustomApplication
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        AppSharePreference.getInstance(applicationContext)
    }

}

