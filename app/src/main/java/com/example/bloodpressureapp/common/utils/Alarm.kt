package com.example.bloodpressureapp.common.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.bloodpressureapp.receivers.NotificationReceiver
import java.util.*

enum class StatusCheck() {
    EARLY, BETWEEN, LATE
}

object Alarm {
    fun setAlarm(context: Context) {
        val broadcastIntent = Intent(context, NotificationReceiver::class.java)
        val pIntent =
            PendingIntent.getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_IMMUTABLE)
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmMgr1 = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val currentTime = System.currentTimeMillis()

        when (checkTimeToSetAlarm(currentTime)) {
            StatusCheck.EARLY -> {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, 7)
                calendar.set(Calendar.MINUTE, 0)

                val calendar1 = Calendar.getInstance()
                calendar1.set(Calendar.HOUR_OF_DAY, 19)
                calendar1.set(Calendar.MINUTE, 0)

                alarmMgr.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pIntent
                )

                alarmMgr1.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar1.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pIntent
                )
            }
            StatusCheck.BETWEEN -> {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, 7)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)

                val calendar1 = Calendar.getInstance()
                calendar1.set(Calendar.HOUR_OF_DAY, 19)
                calendar1.set(Calendar.MINUTE, 0)

                alarmMgr.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pIntent
                )

                alarmMgr1.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar1.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pIntent
                )
            }
            else -> {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, 7)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)

                val calendar1 = Calendar.getInstance()
                calendar1.set(Calendar.HOUR_OF_DAY, 19)
                calendar1.set(Calendar.MINUTE, 0)
                calendar1.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
                alarmMgr.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pIntent
                )

                alarmMgr1.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar1.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pIntent
                )
            }
        }


    }

    private fun checkTimeToSetAlarm(time: Long): StatusCheck {

        val morningCalendar = Calendar.getInstance()
        morningCalendar.set(Calendar.HOUR_OF_DAY, 7)
        morningCalendar.set(Calendar.MINUTE, 0)

        val eveningCalendar = Calendar.getInstance()
        eveningCalendar.set(Calendar.HOUR_OF_DAY, 19)
        eveningCalendar.set(Calendar.MINUTE, 0)

        if (time <= morningCalendar.timeInMillis) {
            return StatusCheck.EARLY
        }
        if (time in morningCalendar.timeInMillis - 1..eveningCalendar.timeInMillis + 1) {
            return StatusCheck.LATE
        }
        if (time >= eveningCalendar.timeInMillis) {
            return StatusCheck.LATE
        }
        return StatusCheck.EARLY

    }

}