package com.example.protodo.example.exercise.alarm

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.component.DeviceManger
import com.example.protodo.component.Notification
import com.example.protodo.main.MainAct


class AlarmAct : ProTodActivity(), ServiceConnection {
    private var service: AlarmService? = null

    override fun onResume() {
        super.onResume()
        "onResume :...".Log()
//        val intent = Intent(this@AlarmAct, AlarmService::class.java)
//        bindService(intent, this@AlarmAct, Context.BIND_AUTO_CREATE)

        val intent = Intent(this@AlarmAct, AlarmService::class.java)
        bindService(intent, this@AlarmAct, Context.BIND_AUTO_CREATE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ServiceCast")
    override fun initiativeView() {
        setContentView(View(this).apply {
            setBackgroundColor(ContextCompat.getColor(this@AlarmAct, R.color.white))
        })

        "initiativeView :...".Log()
        //"show toast :...".toast()
        val pending = Intent(this@AlarmAct, MainAct::class.java)


        //NotificationUtils.show(this@AlarmAct, "title", "message", pending, 1000)
        //RunNotification()
        //NotificationUtils.showNotification(this@AlarmAct)
        val intent = Intent(applicationContext, AlarmService::class.java)
        applicationContext.startService(intent)

        initScreen()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @SuppressLint("CheckResult")
    private fun initScreen() {
//        RxPermissions(this).request(
//            "android.permission.WRITE_SETTINGS"
//        ).subscribe { granted ->
//            if (granted) { // Always true pre-M
//                "I can control the camera now".Log()
//                Settings.System.putInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, 1000)
//            } else {
//                "Oups permission denied".Log()
//            }
//        }

        val deviceManager = DeviceManger.getInstant(this@AlarmAct)
        //turn off device ...
        //deviceManager?.lockNow()
        //deviceManager?.lockNow()

        Notification.checkNotificationServiceRunning(this@AlarmAct)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        //TODO("Not yet implemented")
        (service as? AlarmService.AlarmIBinder)?.getService()?.apply {
            "onServiceConnected".Log()
            this@AlarmAct.service = this
            //Todo : thanh tăt notification :...
            this@AlarmAct.service?.setUpAlarm()
        }
        //Todo :khi keets nối được vơi service thì start alram ...
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        //TODO("Not yet implemented")
        this@AlarmAct.service = null
    }


    override fun onPause() {
        super.onPause()
        if (service != null) {
            unbindService(this@AlarmAct)
            service = null
        }
    }

//    private fun RunNotification() {
//        var contentView: RemoteViews? = null
//        var notification: Notification? = null
//        var notificationManager: NotificationManager? = null
//        val NotificationID = 1005
//        var mBuilder: NotificationCompat.Builder? = null
//
//        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        mBuilder = NotificationCompat.Builder(applicationContext, "notify_001")
//        contentView = RemoteViews(packageName, R.layout.notification_view)
//
//        //contentView!!.setImageViewResource(R.id.icon, R.mipmap.ic_launcher)
//        //val switchIntent = Intent(this, BackgroundService.switchButtonListener::class.java)
//        //val pendingSwitchIntent = PendingIntent.getBroadcast(this, 1020, switchIntent, 0)
//        //contentView!!.setOnClickPendingIntent(R.id.flashButton, pendingSwitchIntent)
//
//        mBuilder.setSmallIcon(R.drawable.ic_alarm)
//        mBuilder.setAutoCancel(false)
//        mBuilder.setOngoing(true)
//        mBuilder.priority = Notification.PRIORITY_HIGH
//        mBuilder.setOnlyAlertOnce(true)
//        mBuilder.build()?.flags = Notification.FLAG_NO_CLEAR or Notification.PRIORITY_HIGH
//        mBuilder.setContent(contentView)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channelId = "channel_id"
//            val channel =
//                NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH)
//            channel.enableVibration(true)
//            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
//            notificationManager.createNotificationChannel(channel)
//            mBuilder.setChannelId(channelId)
//        }
//        notification = mBuilder.build()
//        notificationManager.notify(NotificationID, notification)
//    }

}