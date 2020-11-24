package com.example.protodo.component

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.WifiLock
import android.util.Log
import java.util.*


/**
 * Created by llln on 2017/7/31.
 * 此类为自动连接指定WIFI,主要用于相关测试.
 * 目测只测试过无密码的情况.
 * 提供其他的WIFI信息单并未测试.
 * 目前没有对6.0进行适配.
 * 1.添加相应权限:
 *
 *
 * <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"></uses-permission>
 * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
 *
 *
 * 2.使用方式:
 * (1)初始化WifiAdmin
 * WifiAdmin.getInstance() //获取实例
 * .Init(this)         //初始化
 * .setConnectWifiName("TP") //需要连接的WIFI名称
 * .addCallBack(this) //设置回调接口监听,没有做非空判断
 * .setTimeOutCount(30)    //设置超时次数
 * .setScanTime(1000);      //设置查询间隔,默认为1s.
 *
 *
 * (2)开始连接: WifiAdmin.getInstance().openWifi().startScan().startAutoWIFI();
 *
 *
 * (3)关闭连接: WifiAdmin.getInstance().disconnectWifi();
 *
 *
 *
 */
class WifiAdmin  //提供一个空的构造器
{
    // 定义WifiManager对象
    private var mWifiManager: WifiManager? = null

    // 定义WifiInfo对象
    private var mWifiInfo: WifiInfo? = null

    // 得到网络列表
    // 扫描出的网络连接列表
    var wifiList: List<ScanResult>? = null
        private set

    // 得到配置好的网络
    // 网络连接列表
    var configuration: List<WifiConfiguration>? = null
        private set

    // 定义一个WifiLock
    private var mWifiLock: WifiLock? = null

    //网络连接状态对象
    private var connManager: ConnectivityManager? = null

    //网络信息对象
    private var networkInfo: NetworkInfo? = null

    //Context
    private var mContext: Context? = null

    //需要连接的WIFI,SSID 可以是部分也可以是全部.
    private var mSSIDPart: String? = null

    //获取到WIFI列表的SSID全称
    private var mSSIDFullName: String? = null

    //WIFI连接成功的Callback
    private var mWifiConnectListener: WifiConnectListener? = null

    //定时器 查询WIFI连接状态
    private var mTimer: Timer? = null

    //是否是第一次连接
    private var isFirst = false

    //控制是否继续查询
    private var timerTag = true

    //是否已经开始建立连接
    private var isStartConnect = false

    //查询次数,可以以此来设置超时时间.
    private var timerRunCount = 0

    //查询超时次数
    private var timeOutCount = 0

    //扫描间隔时间
    private var scanTime = 1000

    //连接成功
    var connInfo = false
        private set

    //wcgId,netId.
    var wcgId = 0
        private set

    /**
     * @param context 上下文
     */
    fun Init(context: Context): WifiAdmin? {
        mContext = context
        if (mWifiManager == null) mWifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        // 取得WifiInfo对象
        mWifiInfo = mWifiManager!!.connectionInfo
        return Instance
    }

    /**
     * @param wifiConnectListener wifi连接的监听
     */
    fun addCallBack(wifiConnectListener: WifiConnectListener?): WifiAdmin? {
        mWifiConnectListener = wifiConnectListener
        return Instance
    }

    fun setConnectWifiName(SSID: String?): WifiAdmin? {
        mSSIDPart = SSID
        return Instance
    }

    // 打开WIFI
    fun openWifi(): WifiAdmin? {
        if (!mWifiManager!!.isWifiEnabled) {
            mWifiManager!!.isWifiEnabled = true
        }
        return Instance
    }

    // 关闭WIFI
    fun closeWifi(): WifiAdmin? {
        if (mWifiManager!!.isWifiEnabled) {
            Log.d("tag2", "WIFI 已经关闭")
            mWifiManager!!.isWifiEnabled = false
        }
        return Instance
    }

    // 检查当前WIFI状态
    fun checkState(): Int {
        return mWifiManager!!.wifiState
    }

    // 锁定WifiLock
    fun acquireWifiLock() {
        mWifiLock!!.acquire()
    }

    // 解锁WifiLock
    fun releaseWifiLock() {
        // 判断时候锁定
        if (mWifiLock!!.isHeld) {
            mWifiLock!!.acquire()
        }
    }

    // 创建一个WifiLock
    fun creatWifiLock() {
        mWifiLock = mWifiManager!!.createWifiLock("Test")
    }

    // 指定配置好的网络进行连接
    fun connectConfiguration(index: Int) {
        // 索引大于配置好的网络索引返回
        if (index > configuration!!.size) {
            return
        }
        // 连接配置好的指定ID的网络
        mWifiManager!!.enableNetwork(configuration!![index].networkId, true)
    }

    private fun checkSSID(): Boolean {
        //初始化wifi连接管理器
        if (connManager == null) connManager =
            mContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkInfo = connManager!!.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        //get current SSID
        val ssid = mWifiManager!!.connectionInfo.ssid.replace("\"", "")
        return networkInfo!!.isConnected && ssid.contains(mSSIDPart!!)
    }

    //开始扫描
    @SuppressLint("MissingPermission")
    fun startScan(): WifiAdmin? {
        mWifiManager!!.startScan()
        // 得到扫描结果
        wifiList = mWifiManager!!.scanResults
        // 得到配置好的网络连接
        configuration = mWifiManager!!.configuredNetworks
        return Instance
    }

    fun setTimeOutCount(timeOutCount: Int): WifiAdmin? {
        this.timeOutCount = timeOutCount
        return Instance
    }

    fun setScanTime(scanTime: Int): WifiAdmin? {
        this.scanTime = scanTime
        return Instance
    }

    /**
     *
     */
    fun startAutoWIFI() {
        if (mSSIDPart == null) throw NullPointerException("Did you remember to add the WIFI name?")
        if (connInfo) deleteCurrentWIFI(wcgId)
        if (mTimer == null) mTimer = Timer()
        if (!isFirst) mTimer!!.schedule(CheckWifiTime(), 1000, scanTime.toLong()) //间隔1s再启动
        mWifiConnectListener!!.startConnect()
        //        Log.d("tag2", "开始连接扫描  -- callback ,---Thread---" + Thread.currentThread().getId());
        //timer 只启动一次.
        isFirst = true
        //初始化状态
        timerTag = true
        isStartConnect = false
        timerRunCount = 0
    }

    /**
     * @return 返回指定SSID的全部名称
     */
    private fun lookUpScan(): String? {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < mWifiList.size(); i++) {
//            stringBuilder.append("Index_" + new Integer(i + 1).toString() + ":");
//            // 将ScanResult信息转换成一个字符串包
//            // 其中把包括：BSSID、SSID、capabilities、frequency、level
//            stringBuilder.append((mWifiList.get(i)).toString());
//            stringBuilder.append("/n");
//        }
//        return stringBuilder;
        for (i in wifiList!!.indices) {
            if (wifiList!![i].SSID.contains(mSSIDPart!!)) {
                mSSIDFullName = wifiList!![i].SSID
                return mSSIDFullName
            }
        }
        return ""
    }

    // 得到MAC地址
    val macAddress: String
        get() = if (mWifiInfo == null) "NULL" else mWifiInfo!!.macAddress

    // 得到接入点的BSSID
    val bSSID: String
        get() = if (mWifiInfo == null) "NULL" else mWifiInfo!!.bssid

    // 得到IP地址
    val iPAddress: Int
        get() = if (mWifiInfo == null) 0 else mWifiInfo!!.ipAddress

    // 得到连接的ID
    val networkId: Int
        get() = if (mWifiInfo == null) 0 else mWifiInfo!!.networkId

    // 得到WifiInfo的所有信息包
    val wifiInfo: String
        get() = if (mWifiInfo == null) "NULL" else mWifiInfo.toString()

    // 添加一个网络并连接
    private fun addNetwork(wcg: WifiConfiguration) {
        wcgId = mWifiManager!!.addNetwork(wcg)
        connInfo = mWifiManager!!.enableNetwork(wcgId, true)
        Log.d("tag2", " netID :" + wcgId + "  " + connInfo)
    }

    // 断开网络
    fun disconnectWifi() {
        if (connInfo) deleteCurrentWIFI(wcgId)
        closeWifi() //如果不关闭WIFI的话有较长时间的延迟,需重启WIFI.
        mWifiConnectListener!!.wifiIsDisConnected()
    }

    //不保存当前连接过的WIFI
    fun deleteCurrentWIFI(netId: Int) {
        mWifiManager!!.removeNetwork(netId)
        mWifiManager!!.saveConfiguration()
    }

    fun Destroy() {
        if (null != mTimer) {
            mTimer!!.cancel()
            mTimer = null
        }
    }

    private inner class CheckWifiTime : TimerTask() {
        override fun run() {
            if (!timerTag) {
                //此时表明已经连接成功,此处可以监听WIFI状态.
                if (!checkSSID()) {
                    closeWifi() //如果不关闭WIFI的话有较长时间的延迟,需重启WIFI.
                    mWifiConnectListener!!.wifiIsDisConnected()
                }
                Log.d("tag2", "WIFI State: " + checkSSID())
                return
            }
            //判断扫描的列表当中是否有我们想要连接的WIFI
            if ("" != lookUpScan()) {
                if (!isStartConnect) {
                    mWifiConnectListener!!.onConnect(mSSIDFullName)
                    if (mSSIDFullName != null) addNetwork(CreateWifiInfo(mSSIDFullName!!, "", 0))
                    //                    Log.d("tag2", "正在建立连接 --  " + mSSIDFullName + " ---Thread---" + Thread.currentThread().getId());
                }
                isStartConnect = true
            } else {
                if (timerRunCount >= timeOutCount) {
                    mWifiConnectListener!!.timeOut()
                    //                    Log.d("tag2","查询超时");
                    return
                }
                timerRunCount++
                startScan()
                mWifiConnectListener!!.onScan(timerRunCount)
                //                Log.d("tag2", "列表当中不存在我们想要的WIFI,再次扫描 " + timerRunCount + " 次");
            }
            if (checkSSID()) {
//                Log.d("tag2", "连接成功 -- " + mSSIDFullName + " ---Thread---" + Thread.currentThread().getId());
                mWifiConnectListener!!.connectSuccess(mSSIDFullName)
                timerTag = false
            }
        }
    }

    fun onStop() {
        //不可见时不扫描
        timerTag = false
    }

    //然后是一个实际应用方法，只验证过没有密码的情况：
    //分为三种情况：0和1没有密码2用wep加密3用wpa加密
    private fun CreateWifiInfo(SSID: String, Password: String, Type: Int): WifiConfiguration {
        val config = WifiConfiguration()
        config.allowedAuthAlgorithms.clear()
        config.allowedGroupCiphers.clear()
        config.allowedKeyManagement.clear()
        config.allowedPairwiseCiphers.clear()
        config.allowedProtocols.clear()
        config.SSID = "\"" + SSID + "\""
        val tempConfig = IsExits(SSID)
        if (tempConfig != null) {
            mWifiManager!!.removeNetwork(tempConfig.networkId)
        }
        if (Type == 0) {
            config.preSharedKey = null //非加密wifi
            //      config.preSharedKey = "\"wifi密码\"";//加密wifi
            config.hiddenSSID = true
            config.status = WifiConfiguration.Status.ENABLED
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE) //WPA_PSK  NONE（非加密）
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA)
        }
        if (Type == 1) //WIFICIPHER_NOPASS
        {
            config.wepKeys[0] = ""
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            config.wepTxKeyIndex = 0
        }
        if (Type == 2) //WIFICIPHER_WEP
        {
            config.hiddenSSID = true
            config.wepKeys[0] = "\"" + Password + "\""
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            config.wepTxKeyIndex = 0
        }
        if (Type == 3) //WIFICIPHER_WPA
        {
            config.preSharedKey = "\"" + Password + "\""
            config.hiddenSSID = true
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
            //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
            config.status = WifiConfiguration.Status.ENABLED
        }
        return config
    }


    private fun IsExits(SSID: String): WifiConfiguration? {
        val existingConfigs = mWifiManager!!.configuredNetworks
        if (existingConfigs != null) for (existingConfig in existingConfigs) {
            if (existingConfig.SSID == "\"" + SSID + "\"") {
                return existingConfig
            }
        }
        return null
    }

    interface WifiConnectListener {
        fun wifiIsDisConnected() //WIFI连接超时或者已经断开连接.
        fun timeOut() //超时

        /**
         * 正在扫描回调
         *
         * @param count 扫描次数
         */
        fun onScan(count: Int)

        //开始扫描
        fun startConnect()

        /**
         * 正在尝试连接
         *
         * @param onConnectSSID 连接成功的wifi名称
         */
        fun onConnect(onConnectSSID: String?)

        /**
         * 已经连接成功
         *
         * @param connectSSID 连接成功的wifi名称
         */
        fun connectSuccess(connectSSID: String?)
    }

    companion object {
        @Volatile
        private var Instance: WifiAdmin? = null
        fun getInstance(): WifiAdmin? {
            if (Instance == null) {
                synchronized(WifiAdmin::class.java) {
                    if (Instance == null) {
                        Instance = WifiAdmin()
                    }
                }
            }
            return Instance
        }
    }
}
