package com.example.bloodpressureapp.ui.main.tracker.model

import android.content.res.Resources
import android.os.Parcelable
import com.example.bloodpressureapp.R
import com.example.bloodpressureapp.common.utils.DateTimeUtils
import com.example.bloodpressureapp.data.database.entity.HistoryEntity
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class HistoryModel(
    var id: Int = -1,
    var systolic: Int = 50,
    var diastolic: Int = 50,
    var pulse: Int = 20,
    var date: String = "",
    var time: String = "",
    var status: String = "",
    var notes: MutableList<String> = mutableListOf()

) : Parcelable {
    init {
        val calendar = Calendar.getInstance()
        time =  "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
        date =  DateTimeUtils.getDateConverted(Date(System.currentTimeMillis())).toString()
    }

    fun toHistoryEntity(): HistoryEntity {
        val historyId = if (id == -1) 0 else id
        return HistoryEntity(
            id = historyId,
            systolic = systolic,
            diastolic = diastolic,
            pulse = pulse,
            date = date,
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
            date = date,
            time = time,
            status = status,
            notes = notes.toMutableList()

        )
    }
}