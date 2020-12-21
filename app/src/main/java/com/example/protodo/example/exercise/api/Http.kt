package com.example.protodo.example.exercise.api

import android.content.Context
import android.os.Environment
import com.example.protodo.Utils.Log
import java.io.*

object Http {

    fun index(context: Context): String {
        return convertStreamToString(context, "index.html") ?: ""
    }

    @Throws(IOException::class)
    fun convertStreamToString(context: Context, name: String): String? {

        val inputStream = context.resources.assets.open(name)
        val writer: Writer = StringWriter()
        val buffer = CharArray(2048)
        inputStream.use { _inputStream ->
            val reader: Reader = BufferedReader(InputStreamReader(_inputStream, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }
        return writer.toString()
    }
}