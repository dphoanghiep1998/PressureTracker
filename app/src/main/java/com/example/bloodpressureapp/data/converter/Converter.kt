package com.example.bloodpressureapp.data.converter


import androidx.room.TypeConverter
import java.util.*

class Converter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

}