package com.example.bloodpressureapp.common.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.bloodpressureapp.receivers.NotificationReceiver
import java.util.Calendar

object Alarm {
    fun setAlarm(context: Context){
        val broadcastIntent = Intent(context,NotificationReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(context,0,broadcastIntent,PendingIntent.FLAG_IMMUTABLE)
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,7)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY,pIntent)

    }
}