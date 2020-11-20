package com.example.protodo.common

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.WindowManager

object SizeUtils {
    fun getViewHeight(view: View): Int {
        val wm = view.context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val deviceWidth: Int
        deviceWidth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val size = Point()
            display.getSize(size)
            size.x
        } else {
            display.width
        }
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            deviceWidth,
            View.MeasureSpec.AT_MOST
        )
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
        view.measure(widthMeasureSpec, heightMeasureSpec)
        return view.measuredHeight
    }

    fun getViewWidth(view: View): Int {
        val wm = view.context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val deviceWidth: Int
        deviceWidth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val size = Point()
            display.getSize(size)
            size.x
        } else {
            display.width
        }
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            deviceWidth,
            View.MeasureSpec.AT_MOST
        )
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            0,
            View.MeasureSpec.UNSPECIFIED
        )
        view.measure(widthMeasureSpec, heightMeasureSpec)
        return view.measuredWidth
    }

    /**
     * dip to px
     */
    fun dps2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px to dp
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

}