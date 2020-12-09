package com.example.protodo.notification

import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.example.protodo.Utils.Log


class MyNotifyService : NotificationListenerService() {
    private val receiver = CancelNotificationReceiver()
    private var check = false

    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
        ("in onBind() of MyNotifService").Log()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        //("in onNotificationPosted() of MyNotifService").Log()
        //sbn.Log("StatusBarNotification :... ")

        //Todo : thanh lấy ra tất cả các notification đang hiên thi :...

//        val title = sbn?.notification?.extras?.getString("android.title")
//        val text = sbn?.notification?.extras?.getString("android.text")
//        val package_name = sbn?.packageName
//        ("Notification title is:$title").Log()
//        ("Notification text is:$text").Log()
//        ("Notification Package Name is:$package_name").Log()
//        ("Notification id :${sbn?.id}").Log()
//        "------------------------------------------------------------".Log()

        if (check) {
            cancelAllNotifications()
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        //Todo :thanh lấy ra danh sách notification đã xem :...
        check.Log("check :...")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        "onStartCommand :... ".Log()
        val block = intent.getStringExtra("block")
        ("checking service 1$block").Log()
        if (block == "yes") {
            check = true
        } else if (block == "no") {
            check = false
        }
        ("checking service 1$check").Log()

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        val filter = IntentFilter()
        filter.addAction(packageNames)
        registerReceiver(receiver, filter)
        ("in onCreate() of MyNotifService").Log()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    fun clearAll() {

    }

    companion object {
        val packageNames = "com.example.protodo"
    }
}