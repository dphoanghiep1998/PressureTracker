package com.example.bloodpressureapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = -1,
    var systolic: Int,
    var diastolic: Int,
    var pulse: Int,
    var time: String,
    var date: String,
    var status: String,
    var notes: String
) {

}