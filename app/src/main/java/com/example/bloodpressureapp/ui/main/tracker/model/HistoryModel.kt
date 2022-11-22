package com.example.bloodpressureapp.ui.main.tracker.model

import android.content.res.Resources
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.data.database.entity.HistoryEntity
import java.util.*

class HistoryModel(
    var id: Int = -1,
    var systolic: Int,
    var diastolic: Int,
    var pulse: Int,
    var time: Date,
    var status: String,
    var notes: String

) {
    init {
        if (systolic < 120 && diastolic < 80) {
            status = Resources.getSystem().getString(R.string.normal_blood_pressure)
        } else if (systolic in 120..129 && diastolic < 80) {
            status = Resources.getSystem().getString(R.string.elevated_blood_pressure)
        } else if (systolic >= 180 || diastolic >= 120) {
            status = Resources.getSystem().getString(R.string.dangerous_high_blood_pressure)
        } else if (systolic >= 140 || diastolic >= 90) {
            status = Resources.getSystem().getString(R.string.high_blood_pressure_stage_2)
        } else if (systolic >= 130 || diastolic >= 80) {
            status = Resources.getSystem().getString(R.string.high_blood_pressure_stage_1)
        }

    }

    fun toHistoryEntity(): HistoryEntity {
        val historyId = if (id == -1) 0 else id
        return HistoryEntity(
            id = historyId,
            systolic = systolic,
            diastolic = diastolic,
            pulse = pulse,
            time = time,
            status = status,
            notes = notes


        )
    }

    fun HistoryEntity.toHistoryModel(): HistoryModel {
        return HistoryModel(
            id = id,
            systolic = systolic,
            diastolic = diastolic,
            pulse = pulse,
            time = time,
            status = status,
            notes = notes

        )
    }
}