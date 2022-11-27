package com.example.bloodpressureapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.bloodpressureapp.common.utils.Alarm

class TimeChangeReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if(intent?.action == "android.intent.action.TIME_SET"){
            Alarm.setAlarm(context)
        }
    }
}