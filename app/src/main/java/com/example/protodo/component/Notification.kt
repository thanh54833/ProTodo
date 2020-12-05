package com.example.protodo.component

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import com.example.protodo.Utils.Log

object Notification {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun checkNotificationServiceRunning(context: Context): Boolean {
        val contentResolver: ContentResolver = context.contentResolver
        val enabledNotificationListeners: String =
            Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        val packageName: String = context.packageName
        val isNotificationServiceRunning = enabledNotificationListeners.contains(
            packageName
        )
        if (!isNotificationServiceRunning) {
            context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
        return isNotificationServiceRunning
    }
}