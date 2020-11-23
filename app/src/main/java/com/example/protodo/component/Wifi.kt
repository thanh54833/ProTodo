package com.example.protodo.component

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.WifiLock
import android.util.Log


/**
 * Created by Administrator on 2016/8/28.
 */
class WifiUtil(context: Context) {
    var mWifiManager: WifiManager
    private val mWifiInfo: WifiInfo?
    private var mWifiList: List<ScanResult>? = null
    var configurations: List<WifiConfiguration>? = null
        private set
    private var mWifiLock: WifiLock? = null
    fun openWifi() {
        if (!mWifiManager.isWifiEnabled) {
            mWifiManager.isWifiEnabled = true
        }
    }

    fun checkState(): Int {
        return mWifiManager.wifiState
    }

    fun acquireWifiLoc() {
        mWifiLock!!.acquire()
    }

    fun releaseWifiLock() {
        if (mWifiLock!!.isHeld) {
            mWifiLock!!.acquire()
        }
    }

    fun createWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("test")
    }

    fun connectConfiguration(index: Int): Boolean {

//        if(index > mWificonfiguration.size()) {
//            return;
//        }
        mWifiManager.enableNetwork(index, true)
        mWifiManager.saveConfiguration()
        mWifiManager.reconnect()
        return true
    }

    @SuppressLint("MissingPermission")
    fun startScan() {
        mWifiManager.startScan()
        mWifiList = mWifiManager.scanResults
        configurations = mWifiManager.configuredNetworks
    }

    fun getmWifiList(): List<ScanResult>? {
        return mWifiList
    }

    fun lookUpScan(): StringBuilder {
        val stringBuilder = StringBuilder()
        for (i in mWifiList!!.indices) {
            stringBuilder.append("Index_" + (i + 1).toString() + ":")
            stringBuilder.append(mWifiList!![i].toString())
            stringBuilder.append("/n")
        }
        return stringBuilder
    }

    val macAddress: String
        get() = if (mWifiInfo == null) "NULL" else mWifiInfo.macAddress
    val sSID: String
        get() = if (mWifiInfo == null) "NULL" else mWifiInfo.ssid
    val ipAddress: Int
        get() = mWifiInfo?.ipAddress ?: 0
    val networkId: Int
        get() = mWifiInfo?.networkId ?: 0
    val wifiInfo: String
        get() = mWifiInfo?.toString() ?: "NULL"

    fun addNetWork(wifiConfiguration: WifiConfiguration?): Boolean {
        val wcgID = mWifiManager.addNetwork(wifiConfiguration)
        Log.e("wcgID", wcgID.toString() + "true")
        mWifiManager.enableNetwork(wcgID, true)
        mWifiManager.saveConfiguration()
        mWifiManager.reconnect()
        return true
    }

    fun disconnectWifi(netId: Int) {
        mWifiManager.disableNetwork(netId)
        mWifiManager.disconnect()
    }

    fun createWifiInfo(SSID: String, Password: String, Type: Int): WifiConfiguration {
        val configuration = WifiConfiguration()
        configuration.allowedAuthAlgorithms.clear()
        configuration.allowedGroupCiphers.clear()
        configuration.allowedKeyManagement.clear()
        configuration.allowedPairwiseCiphers.clear()
        configuration.allowedProtocols.clear()
        configuration.SSID = "\"" + SSID + "\""
        val tempConfig = isExsits(SSID)
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId)
        }
        when (Type) {
            1 -> configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            2 -> {
                configuration.hiddenSSID = false
                configuration.wepKeys[0] = "\"" + Password + "\""
                configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)
                configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            }
            3 -> {
                configuration.preSharedKey = "\"" + Password + "\""
                configuration.hiddenSSID = false
                // configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
                configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)
                configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
                configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
                configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
                configuration.status = WifiConfiguration.Status.ENABLED
            }
        }
        return configuration
    }

    @SuppressLint("MissingPermission")
    private fun isExsits(SSID: String): WifiConfiguration? {
        val existingConfigs = mWifiManager.configuredNetworks
        for (existingConfig in existingConfigs) {
            if (existingConfig.SSID == "\"" + SSID + "\"") {
                return existingConfig
            }
        }
        return null
    }

    init {
        mWifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        mWifiInfo = mWifiManager.connectionInfo
    }
}
