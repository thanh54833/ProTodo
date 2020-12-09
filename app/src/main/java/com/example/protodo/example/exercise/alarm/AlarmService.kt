package com.example.protodo.example.exercise.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.protodo.Utils.Log
import com.example.protodo.notification.NotificationUtils
import java.util.*

class AlarmService : Service() {
    var binder: AlarmIBinder? = AlarmIBinder()

    inner class AlarmIBinder : Binder() {
        fun getService() = this@AlarmService
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        "onStartCommand :...".Log()

        return START_NOT_STICKY
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setUpAlarm() {

        "setUpAlarm :...".Log()
        //Todo : hẹn giờ gọi lại service :...
        val intent = Intent(applicationContext, AlarmBroadCast::class.java)
        intent.putExtra(START_NOTIFY, true)

        //applicationContext.startService(intent)
        val pendingIntent = PendingIntent.getBroadcast(baseContext, 0, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val calender = Calendar.getInstance().apply {
            this.add(Calendar.SECOND, 5)
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, calender.timeInMillis, pendingIntent)
        "start alarm :...".Log()


    }

    override fun onBind(intent: Intent?): IBinder? {
        "onBind :...".Log()
        return binder
    }

    companion object {
        const val START_NOTIFY = "START_NOTIFY"
    }
}