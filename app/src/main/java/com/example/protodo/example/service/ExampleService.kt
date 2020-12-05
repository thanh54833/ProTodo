package com.example.protodo.example.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.protodo.Utils.Log

class ExampleService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        "onStartCommand() :... ".Log()


        return super.onStartCommand(intent, flags, startId)
    }
}