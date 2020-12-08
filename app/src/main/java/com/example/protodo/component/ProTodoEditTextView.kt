package com.example.protodo.component

import android.content.Context
import android.text.Spannable
import android.text.style.ImageSpan
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.example.protodo.Utils.Log
import java.lang.Exception
import java.util.regex.Pattern


object ProTodoEditTextView {
    private val spannableFactory = Spannable.Factory.getInstance()
    fun setTextView(editText: AppCompatEditText, text: String) {
        try {
            "find error :...".Log()
            val s = getTextWithImages(editText.context, text)
            "find error :...".Log()
            editText.setText(s)
        } catch (e: Exception) {
            "setTextView :.. ${e.message} ".Log()
        }
    }

    private fun getTextWithImages(context: Context, text: CharSequence): Spannable {
        "find error :...".Log()
        val spannable = spannableFactory.newSpannable(text)
        "find error :...".Log()
        try {
            "find error :...".Log()
            //addImages(context, spannable)
        } catch (e: Exception) {
            "getTextWithImages :... ${e.message} ".Log()
        }
        return spannable
    }

    private fun addImages(context: Context, spannable: Spannable): Boolean {
        "find error :...".Log()
        val refImg = Pattern.compile("\\Q[img src=\\E([a-zA-Z0-9_]+?)\\Q/]\\E")
        "find error :...".Log()
        var hasChanges = false
        "find error :...".Log()
        val matcher = refImg.matcher(spannable)
        "find error :...".Log()
        while (matcher.find()) {
            "find error :...".Log()
            var set = true
            for (span in spannable.getSpans(
                matcher.start(), matcher.end(),
                ImageSpan::class.java
            )) {
                if (spannable.getSpanStart(span) >= matcher.start()
                    && spannable.getSpanEnd(span) <= matcher.end()
                ) {
                    spannable.removeSpan(span)
                } else {
                    set = false
                    break
                }
            }
            "find error :...".Log()
            val resname = spannable.subSequence(matcher.start(1), matcher.end(1)).toString()
                .trim { it <= ' ' }
            val id = context.resources.getIdentifier(resname, "drawable", context.packageName)

            id.Log("id:...")
            "find error :...".Log()

            if (set) {
                hasChanges = true
                spannable.setSpan(
                    ImageSpan(context, id),
                    matcher.start(),
                    matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            "find error :...".Log()
        }
        "find error :...".Log()
        return hasChanges
    }


}