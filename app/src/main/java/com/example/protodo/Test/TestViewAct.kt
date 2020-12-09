package com.example.protodo.Test

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.component.Notification
import com.example.protodo.databinding.TestViewActBinding
import com.example.protodo.main.MainAct
import com.example.protodo.notification.MyNotifyService
import com.example.protodo.palette.PaletteBottomView
import com.example.protodo.permisstion.RxPermissions


class TestViewAct : ProTodActivity() {
    private lateinit var binding: TestViewActBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@TestViewAct, R.layout.test_view_act)

        //bind()
        //bindV2()

        //Todo : tăt kêt nối internet ...
        // Todo : xóa thông báo ...
        //bind3()
        //

        //bind4()
        //Todo : thanh test kill app :..
        bindV2()
    }

    private fun bind4() {
        "bind 4 :...".Log()
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            "ACCESS_FINE_LOCATION :...".Log()
            //wifiManager.configuredNetworks.size.Log("wifiManager.configuredNetworks.size :...")
            //wifiManager.connectionInfo.Log("wifiManager.connectionInfo :...")
            //"wifiManager.connectionInfo :.. ${wifiManager.connectionInfo} ".Log()

            wifiManager.isWifiEnabled = false
            wifiManager.isWifiEnabled.Log("wifiManager.isWifiEnabled :...")
        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private fun bind3() {
        "bind3 :... ".Log()
        this@TestViewAct.packageName.Log("this@TestViewAct.packageName :...")

//        val wifi = WifiAdmin.getInstance()
//        " wifi?.wifiList :.. ${wifi?.wifiList} ".Log()
//
//        val gps = GpsUtils(this@TestViewAct
//
//
//        "can gps :... ${gps.canToggleGPS()} ".Log()
//        gps.turnGPSOn()
//        val intent = Intent("android.location.GPS_ENABLED_CHANGE")
//        intent.putExtra("enabled", true)
//        sendBroadcast(intent)

        //Todo : thanh tao ra 1 cái thông báo :..

        val intent = Intent(this@TestViewAct, MainAct::class.java)
        showNotification(this@TestViewAct, "title", "message", intent, 1000)


        //showNotification(this@TestViewAct, "title", "message", intent, 1000)
        //showNotification(this@TestViewAct, "title", "message", intent, 1000)


//        MainScope().launch {
//            delay(5000)
//            NotificationManagerCompat.from(this@TestViewAct).cancelAll()
//            cancelNotification()
//        }

        Notification.checkNotificationServiceRunning(this@TestViewAct)

        //NotificationManagerCompat.from(this@TestViewAct).areNotificationsEnabled().Log(" NotificationManagerCompat.from(this@TestViewAct).cancelAll() :....")

        //MyNotifyService().cancelAllNotifications()
        //NotificationManagerCompat.from(this@TestViewAct).cancelAll()


        val intents = Intent(this@TestViewAct, MyNotifyService::class.java)
        //val intents = Intent()
        intents.action = MyNotifyService.packageNames
        intents.putExtra("block", "yes")
        sendBroadcast(intent)
        startService(intents)

    }


    private fun showNotification(
        context: Context,
        title: String?,
        message: String?,
        intent: Intent?,
        reqCode: Int
    ) {
        val pendingIntent =
            PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT)
        val CHANNEL_ID = "channel_name" // The id of the channel.
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Channel Name" // The user-visible name of the channel.
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(mChannel)
        }
        notificationManager.notify(
            reqCode,
            notificationBuilder.build()
        ) // 0 is the request code, it should be unique id
    }

    private fun cancelNotification() {
        val nMgr = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nMgr.cancel(100)
        nMgr.cancelAll()
    }

    private fun bind() {
        binding.contentLayout.addView(PaletteBottomView(context = this@TestViewAct))

    }

    //Todo : Thanh làm việc với network  ....
    // 1) xin quyền clear app  ....

    @SuppressLint("CheckResult")
    private fun bindV2() {

        RxPermissions(this).request(
            "android.permission.KILL_BACKGROUND_PROCESSES"
        ).subscribe { granted ->
            if (granted) { // Always true pre-M
                "I can control the camera now :...".Log()

                val actvityManager = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager
                val procInfos = actvityManager.runningAppProcesses

                procInfos.size.Log(" procInfos.size:...")
                procInfos.forEach { _item ->

                    actvityManager.killBackgroundProcesses(_item.processName.Log("_item.processName :..."))

                }
                actvityManager.killBackgroundProcesses("com.android.chrome")
                actvityManager.killBackgroundProcesses("com.google.chromeremotedesktop")
                for (runningProInfo in procInfos) {
//                    "find error :... ${runningProInfo.processName}".Log()
//                    if (runningProInfo.processName == ) {
//                        "find error :...".Log()
//                    }
                }
            } else {
                "Oups permission denied".Log()
            }
        }

        killAppByPackage("com.android.chrome")
        killAppByPackage("com.google.chromeremotedesktop")

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun killAppByPackage(packageToKill: String) {
        val packages: List<ApplicationInfo>
        val pm: PackageManager = packageManager
        //get a list of installed apps.
        packages = pm.getInstalledApplications(0)
        val mActivityManager =
            this@TestViewAct.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val myPackage = applicationContext.packageName
        for (packageInfo in packages) {
            if (packageInfo.flags and ApplicationInfo.FLAG_SYSTEM == 1) {
                continue
            }
            if (packageInfo.packageName == myPackage) {
                continue
            }
            if (packageInfo.packageName == packageToKill) {
                mActivityManager.killBackgroundProcesses(packageInfo.packageName)
            }
        }
    }


    companion object {
        fun getIntent(context: Context): Intent {
            var intent = Intent(context, TestViewAct::class.java)

            return intent
        }
    }
}