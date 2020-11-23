package com.example.protodo.Test

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.component.WifiUtil
import com.example.protodo.databinding.TestViewActBinding
import com.example.protodo.palette.PaletteBottomView
import com.example.protodo.permisstion.RxPermissions


class TestViewAct : ProTodActivity() {
    private lateinit var binding: TestViewActBinding

    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@TestViewAct, R.layout.test_view_act)
        //bind()
        //bindV2()
        //Todo : tăt kêt nối internet ...
        bind3()
    }

    @SuppressLint("CheckResult", "MissingPermission")
    private fun bind3() {
//        RxPermissions(this).request(
//            "android.permission.MODIFY_PHONE_STATE"
//        ).subscribe { granted ->
//            if (granted) { // Always true pre-M
//                "I can control the camera now".Log()
//                try {
//                    val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
//                    val methodSet =
//                        Class.forName(tm.javaClass.name)
//                            .getDeclaredMethod("setDataEnabled", Boolean.TYPE)
//                    methodSet.invoke(tm, true)
//                } catch (e: Exception) {
//                    e.message.Log(" e.message :...")
//                }
//
//            } else {
//                "Oups permission denied".Log()
//            }
//        }

//        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
//        //" ${wifiManager.wifiState} wifiManager.wifiState :...".Log()
//        wifiManager.isWifiEnabled = false
//        "${wifiManager.wifiState} wifiManager.wifiState :...".Log()
        val wifi = WifiUtil(this@TestViewAct)
        wifi.startScan()
        wifi.getmWifiList()?.size?.Log(" wifi.getmWifiList() :...")
        for (configuration in wifi.configurations!!) {
            (
                    "ssid:" + configuration.SSID + "--id:" + configuration.networkId +
                            "--priority" + configuration.priority + "--allowedAuthAlgorithms:" + configuration.allowedAuthAlgorithms +
                            "--allowedGroupCiphers:" + configuration.allowedGroupCiphers + "--allowedKeyManagement:" + configuration.allowedKeyManagement +
                            "--allowedAuthAlgorithms:" + configuration.allowedAuthAlgorithms
                            + "--allowedPairwiseCiphers:" + configuration.allowedPairwiseCiphers
                            + "--hiddenSSID:" + configuration.hiddenSSID
                            + "--wepTxKeyIndex:" + configuration.wepTxKeyIndex
                            + "--wepKeys:" + configuration.wepKeys[0]
                            + "--preSharedKey" + configuration.preSharedKey
                            + "--status:" + configuration.status
                    ).Log()
        }
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
                "I can control the camera now".Log()

                val actvityManager = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager
                val procInfos = actvityManager.runningAppProcesses

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