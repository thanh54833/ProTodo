package com.example.protodo.component

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.protodo.component.turn_off.DeviceAdmin
import com.example.protodo.Utils.Log

object DeviceManger {
    private var deviceManger: DevicePolicyManager? = null
    fun getInstant(context: Context): DevicePolicyManager? {
        return if (isAction(context)) {
            deviceManger
        } else {
            null
        }
    }

    private fun isAction(context: Context): Boolean {
        deviceManger =
            context.getSystemService(AppCompatActivity.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val compName = ComponentName(context, DeviceAdmin::class.java).Log("compName:..")
        val active: Boolean? = deviceManger?.isAdminActive(compName)
        //if this is not action then start request permission :...
        if (active.Log("active:...") == false) {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName)
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "You should enable the app!")
            context.startActivity(intent)
        }
        return true
    }
}