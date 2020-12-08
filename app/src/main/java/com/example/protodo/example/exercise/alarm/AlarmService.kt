package com.example.protodo.example.exercise.alarm

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.protodo.Utils.Log

class AlarmService : Service() {
    var binder: AlarmIBinder? = null

    inner class AlarmIBinder : Binder() {
        fun getService() = this@AlarmService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        "onStartCommand :...".Log()
        //Todo : show notify :...



        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        "onBind :...".Log()
        return binder
    }
}