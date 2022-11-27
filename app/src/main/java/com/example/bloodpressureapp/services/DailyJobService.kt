package com.example.bloodpressureapp.services

import android.app.job.JobParameters
import android.app.job.JobService

class DailyJobService:JobService() {
    override fun onStartJob(p0: JobParameters?): Boolean {

        return false
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return true
    }
}