package com.example.protodo.notification.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.protodo.R

//import com.example.protodo.databinding.NotificationViewBinding


class NotificationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomView(context, attrs, defStyleAttr) {
    //private var binding: NotificationViewBinding? = null
    override fun initializeCustomView() {
        //binding = getViewBinding(context, R.layout.notification_view, this)


    }
}

abstract class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        initializeCustomView()
    }

    abstract fun initializeCustomView()
    fun <VDB : ViewDataBinding> getViewBinding(
        context: Context,
        layoutId: Int,
        viewGroup: ViewGroup
    ): VDB? {
        val view = View.inflate(context, layoutId, viewGroup)
        return DataBindingUtil.getBinding(view)
    }
}



