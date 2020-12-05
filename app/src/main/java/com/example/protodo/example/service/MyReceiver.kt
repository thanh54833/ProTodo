package com.example.protodo.example.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.protodo.Utils.Log


class ConnectionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        //Toast.makeText(context, "Action: " + intent.action, Toast.LENGTH_SHORT).show()
        "MyReceiver :... ${intent.action} ".Log()


    }
}

class MyPhoneReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.extras?.apply {
            getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
        }
    }
}

class MyBroadcastReceiver : BroadcastReceiver() {
    private var vibrator: Vibrator? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        "onReceive :... ".Log()
        val isVibrate =
            intent?.extras?.getBoolean("VIBRATE", false).Log("intent?.extras?.getBoolean :...")
        if (isVibrate == true) {
            startVibrate(context)
        } else {
            stopVibrate(context)
        }
    }

    //Todo : thanh bật rung :...
    private fun startVibrate(context: Context?) {
        //"\"Don't panik but your time is up!!!!.\",".Log()
        // Vibrate the mobile phone
        vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator != null && (vibrator?.hasVibrator() == true)) {
            vibrateFor500ms()
            customVibratePatternNoRepeat()
            customVibratePatternRepeatFromSpecificIndex()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createOneShotVibrationUsingVibrationEffect()
                createWaveFormVibrationUsingVibrationEffect()
                createWaveFormVibrationUsingVibrationEffectAndAmplitude()
            }
            //vibrator?.cancel()
        } else {
            Toast.makeText(context, "Device does not support vibration", Toast.LENGTH_SHORT)
                .show();
        }
    }

    //Todo : thanh tắt rung :...
    private fun stopVibrate(context: Context?) {
        vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator?.cancel()
    }

    private fun vibrateFor500ms() {
        vibrator?.vibrate(500)
    }

    private fun customVibratePatternNoRepeat() {
        // 0 : Start without a delay
        // 400 : Vibrate for 400 milliseconds
        // 200 : Pause for 200 milliseconds
        // 400 : Vibrate for 400 milliseconds
        val mVibratePattern = longArrayOf(0, 400, 200, 400)

        // -1 : Do not repeat this pattern
        // pass 0 if you want to repeat this pattern from 0th index
        vibrator?.vibrate(mVibratePattern, -1)
    }

    private fun customVibratePatternRepeatFromSpecificIndex() {
        val mVibratePattern = longArrayOf(0, 400, 800, 600, 800, 800, 800, 1000)

        // 3 : Repeat this pattern from 3rd element of an array
        vibrator?.vibrate(mVibratePattern, 3)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createOneShotVibrationUsingVibrationEffect() {
        // 1000 : Vibrate for 1 sec
        // VibrationEffect.DEFAULT_AMPLITUDE - would perform vibration at full strength
        val effect = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator?.vibrate(effect)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createWaveFormVibrationUsingVibrationEffect() {
        val mVibratePattern = longArrayOf(0, 400, 1000, 600, 1000, 800, 1000, 1000)
        // -1 : Play exactly once
        val effect = VibrationEffect.createWaveform(mVibratePattern, -1)
        vibrator?.vibrate(effect)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createWaveFormVibrationUsingVibrationEffectAndAmplitude() {
        val mVibratePattern = longArrayOf(0, 400, 800, 600, 800, 800, 800, 1000)
        val mAmplitudes = intArrayOf(0, 255, 0, 255, 0, 255, 0, 255)
        // -1 : Play exactly once
        if (vibrator?.hasAmplitudeControl() == true) {
            val effect = VibrationEffect.createWaveform(mVibratePattern, mAmplitudes, -1)
            vibrator?.vibrate(effect)
        }
    }
}