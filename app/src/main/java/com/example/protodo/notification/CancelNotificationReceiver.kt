package com.example.protodo.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.protodo.Utils.Log

class CancelNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        "CancelNotificationReceiver :...".Log()

        intent?.takeIf { it.action != null }?.apply {

            val block = intent.getStringExtra("block")
            block.Log("block :....")

        }
    }
}