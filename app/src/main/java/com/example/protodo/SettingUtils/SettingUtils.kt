package com.example.protodo.SettingUtils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.os.Build
import androidx.fragment.app.FragmentActivity
import com.example.protodo.Utils.Log


class SettingUtils(var context: Context) {
    //Todo : tắt mở wifi ...
    fun setEnableBluetooth(isEnable: Boolean) {


    }

//    //Todo : đóng tất cả app đang chay...
//    fun clearAllApp() {
//
//
//    }


    @SuppressLint("QueryPermissionsNeeded")
    @Throws(PackageManager.NameNotFoundException::class)
    fun clearAllApp(): List<String>? {
        val componentList: MutableList<String> = ArrayList()
        (context as? FragmentActivity)?.apply {
            val mainIntent = Intent(Intent.ACTION_MAIN, null)
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            val ril: List<ResolveInfo> =
                packageManager.queryIntentActivities(mainIntent, 0)
            var name: String? = null
            for (ri in ril) {
                if (ri.activityInfo != null) {
                    val res: Resources =
                        packageManager.getResourcesForApplication(ri.activityInfo.applicationInfo)
                    if (ri.activityInfo.labelRes != 0) {
                        name = res.getResourcePackageName(ri.activityInfo.labelRes)
                        componentList.add(name)
                        "package name :... $name".Log()
                    } else {
                        name = ri.activityInfo.applicationInfo.packageName
                        componentList.add(name)
                        "package name :... $name".Log()
                    }

//                    if (appInForeground()) {
//                        // clearAppData(name)
//                        "isForeground(name) :... $name ___ ${appInForeground(name)} ".Log()
//                    }
                }
            }
        }
        return componentList
    }

    fun clearAppData(packageName: String) {
        try {
            (context as? FragmentActivity)?.apply {
                // clearing app data
                if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                    (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).clearApplicationUserData() // note: it has a return value!
                } else {
                    val runtime = Runtime.getRuntime()
                    runtime.exec("pm clear $packageName")
                }
            }
        } catch (e: Exception) {
            "e messeage : ${e.message} ".Log()
            e.printStackTrace()
        }
    }

//    fun isForeground(myPackage: String): Boolean {
//        val manager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
//        val runningTaskInfo = manager.getRunningTasks(1)
//        val componentInfo = runningTaskInfo[0].topActivity
//
//        return componentInfo?.packageName == myPackage
//    }

    private fun applicationInForeground(packageName: String): Boolean {
        var isActivityFound = false
        (context as? FragmentActivity)?.apply {
            val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager?
            val services =
                activityManager!!.runningAppProcesses
            if (services[0].processName.equals(
                    packageName,
                    ignoreCase = true
                ) && services[0].importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            ) {
                isActivityFound = true
            }
        }
        return isActivityFound
    }

    fun isAppRunning(
        context: Context,
        packageName: String
    ): Boolean {
        val activityManager =
            context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val procInfos =
            activityManager.runningAppProcesses
        if (procInfos != null) {
            for (processInfo in procInfos) {
                if (processInfo.processName == packageName) {
                    return true
                }
            }
        }
        return false
    }

//    fun appInForeground(): Boolean {
//        val activityManager =
//            context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
//        val runningAppProcesses =
//            activityManager.runningAppProcesses ?: return false
//        for (runningAppProcess in runningAppProcesses) {
//
//            "runningAppProcess :.. ${runningAppProcess.processName} ".Log()
//
//            if (runningAppProcess.processName == context.packageName && runningAppProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                return true
//            }
//        }
//        return false
//    }

}