package com.example.protodo.notification.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.protodo.R
import com.example.protodo.databinding.NotificationViewV2Binding

class NotificationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomView<LinearLayout, NotificationViewV2Binding>(
    context,
    attrs,
    defStyleAttr,
    R.layout.notification_view_v2
) {
    private var binding: NotificationViewV2Binding? = null
    override fun initializeCustomView() {
        //binding = getViewBinding(context, R.layout.notification_view, this)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //TODO("Not yet implemented")
    }
}

abstract class getCustomView<T : ViewGroup>() {
    // FrameLayout(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)

}


abstract class CommonView<VG : ViewGroup> : FrameLayout {
    constructor(context: Context?) : super(context!!) {


    }

    constructor (context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {

    }

    constructor (
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context!!, attrs, defStyleAttr) {

    }
}

abstract class CustomView<VG : ViewGroup, VBD : ViewDataBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.Halo_TextInputLayout,
    layoutId: Int
) : CommonView<VG>(context, attrs, defStyleAttr) {
    private var binding: VBD? = null

    init {
        binding = getViewBinding(context, layoutId, this)
        initializeCustomView()
    }

    abstract fun initializeCustomView()
    private fun <VDB : ViewDataBinding> getViewBinding(
        context: Context,
        layoutId: Int,
        viewGroup: ViewGroup
    ): VDB? {
        val view = View.inflate(context, layoutId, viewGroup)
        return DataBindingUtil.getBinding(view)
    }
}



