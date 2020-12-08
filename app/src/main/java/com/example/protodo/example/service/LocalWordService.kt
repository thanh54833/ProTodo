package com.example.protodo.example.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*

class LocalWordService : Service() {
    private val mBinder: IBinder = MyBinder()
    val resultList = mutableListOf<String>()
    private var counter = 1

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        addResultValues()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        addResultValues()
        return mBinder
    }

    inner class MyBinder : Binder() {
        val service: LocalWordService = this@LocalWordService
    }

    private fun addResultValues() {
        val random = Random()
        val input = listOf("Linux", "Android", "iPhone", "Windows7")
        resultList.add(input[random.nextInt(3)] + " " + counter++)
        if (counter == Int.Companion.MAX_VALUE) {
            counter = 0
        }
    }
}