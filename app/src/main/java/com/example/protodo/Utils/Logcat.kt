package com.example.protodo.Utils

import android.text.TextUtils
import android.util.Log
import android.view.View
import com.example.protodo.BuildConfig
import com.google.gson.Gson
import java.util.*


fun <T> T.Log(message: String = ""): T {
    //remove all log..
    var messenger = message
    (this as? Objects)?.apply {
        this::class.java.name.apply {
            if (!TextUtils.isEmpty(this)) {
                messenger = "$this : "
            }
        }
    }
    logCat("$messenger ${Gson().toJson(this)}")
    return this
}


fun <T> T.Log(message: String, handle: (it: T) -> String): T {
    handle(this).let { _result ->
        _result.Log(message)
    }
    return this
}

private fun logCat(messenger: String) {
    if (BuildConfig.DEBUG) {
        Log.i(tag(), messenger)
    }
}

private fun tag(): String? {
    fun getNumberStack(): Int {
        var index = 0
        Thread.currentThread().stackTrace.filterIndexed { _index, _item ->
            return@filterIndexed if (_item.fileName.equals(
                    "Logcat.kt",
                    ignoreCase = true
                )
            ) {
                index = _index
                true
            } else {
                false
            }
        }
        return index
    }
    return Thread.currentThread().stackTrace.getOrNull(getNumberStack())?.let { element ->
        "(${element.fileName}:${element.lineNumber})I/~~~"
    }
}

fun View.disableFor1Sec() {
    try {
        this.isEnabled = false
        this.alpha = 0.5.toFloat()
        this.postDelayed({
            try {
                this.isEnabled = true
                this.alpha = 1.0.toFloat()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }, 1000)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}
