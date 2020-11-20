package com.example.protodo.typeface

import android.graphics.Typeface
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan


object TypefaceUtils {

    fun getNormal(): StyleSpan {
        return StyleSpan(Typeface.NORMAL)
    }

    fun getBold(): StyleSpan {
        return StyleSpan(Typeface.BOLD)
    }

    fun getItalic(): StyleSpan {
        return StyleSpan(Typeface.ITALIC)
    }

    fun getUnderline(): UnderlineSpan {
        return UnderlineSpan()
    }


}