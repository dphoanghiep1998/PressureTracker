package com.example.bloodpressureapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = -1,
    var systolic: Int,
    var diastolic: Int,
    var pulse: Int,
    var time: Date,
    var status: String,
    var notes:String
) {

}