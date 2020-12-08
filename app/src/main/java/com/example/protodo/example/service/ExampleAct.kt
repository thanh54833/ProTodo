package com.example.protodo.example.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.ExampleActBinding


class ExampleAct : ProTodActivity() {
    private lateinit var binding: ExampleActBinding
    private var receiver: ConnectionReceiver? = null
    private var intentFilter: IntentFilter? = null
    private var intentVibrate: Intent? = null

    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@ExampleAct, R.layout.example_act)
        receiver = ConnectionReceiver()
        intentFilter = IntentFilter("com.journaldev.broadcastreceiver.SOME_ACTION")
        val intent = Intent("com.journaldev.broadcastreceiver.SOME_ACTION")
        sendBroadcast(intent)
        //Todo : thanh cho rung trên service rồi tắt :...
        //vibrateIntent()
        //initAction()
        //Todo : thanh start service :....
        val intentService = Intent(this@ExampleAct, ExampleService::class.java)
        startService(intentService)
    }


    private fun initAction() {
        binding.clickBt.setOnClickListener {
            intentVibrate?.putExtra("VIBRATE", false)
            sendBroadcast(intentVibrate)
        }
    }

    private fun vibrateIntent() {
        intentVibrate = Intent(this, MyBroadcastReceiver::class.java)
        intentVibrate?.putExtra("VIBRATE", true)
        val pendingIntent = PendingIntent.getBroadcast(this@ExampleAct, 1000, intentVibrate, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5, pendingIntent)
        //val current = System.currentTimeMillis()
        //.Log("System.currentTimeMillis() :...")
        // val currentV2 = alarmManager.nextAlarmClock.triggerTime
        //.Log("alarmManager.nextAlarmClock.showIntent :...")
        //"current time :... ${currentV2 - current} ".Log()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, intentFilter);
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}