package com.example.protodo.example.exercise.alarm

import android.annotation.SuppressLint
import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.main.MainAct
import com.example.protodo.notification.NotificationUtils
import java.util.Calendar.SECOND
import java.util.Calendar.getInstance


class AlarmAct : ProTodActivity(), ServiceConnection {
    private var service: AlarmService? = null
    override fun onResume() {
        super.onResume()
        val inten = Intent(this@AlarmAct, AlarmService::class.java)
        bindService(inten, this@AlarmAct, Context.BIND_AUTO_CREATE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ServiceCast")
    override fun initiativeView() {
        setContentView(View(this).apply {
            setBackgroundColor(ContextCompat.getColor(this@AlarmAct, R.color.white))
        })
        //Todo : hẹn giờ gọi lại service :...
        val intent = Intent(applicationContext, AlarmService::class.java)
        //applicationContext.startService(intent)
        val pendingIntent = PendingIntent.getService(this@AlarmAct, 0, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val calender = getInstance().apply {
            this.add(SECOND, 5)
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, calender.timeInMillis, pendingIntent)

        "start alarm :...".Log()
        "show toast :...".toast()

        val pending = Intent(this@AlarmAct, MainAct::class.java)
        //NotificationUtils.show(this@AlarmAct, "title", "message", pending, 1000)
        //RunNotification()
        NotificationUtils.showNotification(this@AlarmAct)
    }


    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        //TODO("Not yet implemented")
        "onServiceConnected".Log()
        this@AlarmAct.service = (service as? AlarmService.AlarmIBinder)?.getService()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        //TODO("Not yet implemented")
        this@AlarmAct.service = null
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