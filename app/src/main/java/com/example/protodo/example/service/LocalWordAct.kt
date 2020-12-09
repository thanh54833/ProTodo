package com.example.protodo.example.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity

class LocalWordAct : ProTodActivity(), ServiceConnection {
    private var service: LocalWordService? = null
    override fun onResume() {
        super.onResume()
        val intent = Intent(this@LocalWordAct, LocalWordService::class.java)
        bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun initiativeView() {
        "initiativeView :...".Log()

        val service = Intent(applicationContext, LocalWordService::class.java)
        applicationContext.startService(service)
        "service?.resultList :... ${this@LocalWordAct.service?.resultList} ".Log()

    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        //TODO("Not yet implemented")
        "onServiceConnected :..".Log()
        val binder = service as LocalWordService.MyBinder
        this@LocalWordAct.service = binder.service
        //this@LocalWordAct.service?.resultList.Log("service?.resultList :..."s)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        //TODO("Not yet implemented")
        "onServiceDisconnected :..".Log()
        this@LocalWordAct.service = null
    }
}