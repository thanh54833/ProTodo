package com.example.protodo.example.exercise.api

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.ApiActBinding
import java.io.IOException


class ApiAct : ProTodActivity() {
    var server: HelloServer? = null
    lateinit var binding: ApiActBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initiativeView() {
        "ApiAct :...".Log()
        binding = DataBindingUtil.setContentView(this@ApiAct, R.layout.api_act)
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val ipAddress = wifiManager.connectionInfo.ipAddress
        binding.textView.text = "thanh thanh"
        server = HelloServer()
        val formattedIpAddress = String.format(
            "%d.%d.%d.%d", ipAddress and 0xff,
            ipAddress shr 8 and 0xff,
            ipAddress shr 16 and 0xff, ipAddress shr 24 and 0xff
        )
        server?.result = { _uri ->
            binding.textView.text = _uri
        }
        server?.context = this@ApiAct
        //http://10.0.5.106:8080/
        val url = "http://$formattedIpAddress:${8080}/"
        val urlHome = "http://$formattedIpAddress:${8080}/home"
        "formattedIpAddress :... $url  ".Log()
        "formattedIpAddress :... $urlHome  ".Log()
        try {
            server!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun setupUI() {
    }

    override fun observeViewModel() {

    }

    class HelloServer : NanoHTTPD(8080) {
        var result: (msg: String) -> Unit = { _ -> }
        var context: Context? = null
        override fun serve(session: IHTTPSession): Response {
            val method = session.method
            val page = getPage(session.uri)//.Log("page :...")
            var msg = ""
            //return newSingleThreadContext("Requested itemId = " + itemIdRequestParameter)
            //"uri :... ${session.uri} ".Log()
            when (page) {
                Page.DEFAULT -> {
                    msg = "<html><body><h1>Hello server thanh thanh </h1>\n"
                    msg += "</body></html>\n"
                }
                Page.HOME -> {
                    context?.let { msg = Http.index(it) }
                }
            }
            result("method :... $method url:...${session.uri}")
            return Response(msg)
        }

        private fun getPage(uri: String): Page {
            var page: Page = Page.DEFAULT
            when {
                uri.contains("home") -> {
                    page = Page.HOME
                }
            }
            return page
        }
    }
}