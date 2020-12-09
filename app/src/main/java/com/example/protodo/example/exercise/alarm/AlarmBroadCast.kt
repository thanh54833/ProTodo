package com.example.protodo.example.exercise.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.protodo.Utils.Log
import com.example.protodo.notification.NotificationUtils

class AlarmBroadCast : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        "AlarmBroadCast :...".Log()
        val isStart = intent?.getBooleanExtra(AlarmService.START_NOTIFY, false).Log("isStart:...")
        if (isStart == true) {
            //Todo :  show notify :...
            if (context != null) {
                NotificationUtils.showNotification(context)
            }
        }
    }
}