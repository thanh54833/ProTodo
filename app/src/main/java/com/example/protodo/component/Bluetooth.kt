package com.example.protodo.component

import android.bluetooth.BluetoothAdapter


object Bluetooth {
    fun isConnectBluetooth(): Boolean {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return mBluetoothAdapter.isEnabled
    }

    fun setEnable(isEnable: Boolean) {
        if (isEnable) {
            enableBluetooth()
        } else {
            disableBluetooth()
        }
    }

    //method to enable bluetooth
    private fun enableBluetooth() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!mBluetoothAdapter.isEnabled) {
            mBluetoothAdapter.enable()
        }
    }

    //method to disable bluetooth
    private fun disableBluetooth() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter.isEnabled) {
            mBluetoothAdapter.disable()
        }
    }
}