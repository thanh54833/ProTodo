package com.example.protodo.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.protodo.R


object NotificationUtils {
    fun show(
        context: Context,
        title: String?,
        message: String?,
        intent: Intent?,
        reqCode: Int
    ) {
        val pendingIntent =
            PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT)
        val CHANNEL_ID = "channel_name" // The id of the channel.
        val contentView = RemoteViews(
            context.packageName,
            R.layout.notification_view
        )
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                //.setContent(contentView)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
        notificationBuilder.build().contentView = contentView

        val notificationManager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Channel Name" // The user-visible name of the channel.
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(mChannel)
        }
        notificationManager.notify(
            reqCode,
            notificationBuilder.build()?.apply {
                //bigContentView = contentView

            }
        ) // 0 is the request code, it should be unique id
    }

    //Todo : Chú ý : trong layout remove view chỉ không chứa các view app compat ...
    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(context: Context) {
        val notificationManager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "tutorialspoint_01"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "My Notifications",
                NotificationManager.IMPORTANCE_MAX
            )
            // Configure the notification channel.
            notificationChannel.description = "Sample Channel description"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        val contentView = RemoteViews(context.packageName, R.layout.notification_view)
        contentView.setTextViewText(R.id.title_tv, "Mouse ready to use")
        contentView.setTextViewText(
            R.id.content_tv,
            "YAY ! You can do some Magic now ! Start using your Magic Mouse !"
        )

        contentView.setOnClickPendingIntent(R.id.done_bt, getPendingSelfIntent(context, ""))
        notificationBuilder
            .setAutoCancel(false)
            .setDefaults(Notification.FLAG_NO_CLEAR)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(android.R.drawable.ic_delete)
            .setContent(contentView)
            .setOngoing(true)
            .setPriority(Notification.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
//            .setTicker("Tutorialspoint") //.setPriority(Notification.PRIORITY_MAX)
//            .setContentTitle("sample notification")
//            .setContentText("This is sample notification")
//            .setContentInfo("Information")
        notificationManager.notify(1, notificationBuilder.build())
    }

    private fun getPendingSelfIntent(context: Context?, action: String?): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }
}