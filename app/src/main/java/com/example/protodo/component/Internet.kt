package com.example.protodo.component

import android.content.Context
import android.net.wifi.WifiManager
import android.provider.Settings
import androidx.core.content.ContextCompat.getSystemService
import com.example.protodo.Utils.Log


object Internet {

    fun setEnable(context: Context, isEnable: Boolean) {
        val wifi =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager?
        wifi!!.isWifiEnabled = isEnable

        if (wifi.isWifiEnabled) {
            ("Toggle Wifi Enabled going to disable").Log()
            wifi.isWifiEnabled = false;
        } else {
            ("Wifi Disabled going to enable ").Log()
            if (Settings.System.getInt(
                    context.contentResolver,
                    Settings.System.AIRPLANE_MODE_ON,
                    0
                ) != 0
            ) {
                Runtime.getRuntime()
                    .exec("adb shell settings put global airplane_mode_radios cell,nfc,wimax,bluetooth")
            }

            try {
                Runtime.getRuntime()
                    .exec("adb shell settings put global airplane_mode_radios cell,nfc,wimax,bluetooth")
            } catch (e: Exception) {
                e.message.Log("e.message :...")
            }

            wifi.isWifiEnabled = true;
            ("WI: " + wifi.isWifiEnabled).Log()
        }
    }

}