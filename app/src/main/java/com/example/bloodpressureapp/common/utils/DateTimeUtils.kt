package com.example.bloodpressureapp.common.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    fun getDateConverted(date: Date?): String? {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        return formatter.format(date).toString()
    }

    fun getDateConvertedToResult(date: Date?): String? {
        val formatter = SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.ENGLISH)
        return formatter.format(date).toString()
    }

    fun convertToDateMonth(date: Date): String {
        val cCalendar = Calendar.getInstance()
        val dCalendar = Calendar.getInstance()
        dCalendar.timeInMillis = date.time
        if (cCalendar.get(Calendar.DATE) == dCalendar.get(Calendar.DATE)) {
            return "Today"
        }
        val formatter = SimpleDateFormat("MM-dd", Locale.ENGLISH)
        return formatter.format(date).toString()
    }
    fun convertTimeStringToCalendar(time:String):Date{
        try {
            val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.US)
            return simpleDateFormat.parse(time)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Date()
    }
    fun convertDateStringToCalendar(time:String):Long{
        try {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy",Locale.US)
            simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            return simpleDateFormat.parse(time).time
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Date().time
    }
}