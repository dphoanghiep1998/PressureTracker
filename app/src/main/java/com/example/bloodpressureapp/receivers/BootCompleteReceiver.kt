package com.example.bloodpressureapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.bloodpressureapp.common.utils.Alarm

class BootCompleteReceiver :BroadcastReceiver(){
    override fun onReceive(contex: Context?, intent: Intent?) {

            Log.d("TAG", "onReceive: ")
            contex?.let { Alarm.setAlarm(it) }

    }
}