package com.example.protodo.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.protodo.R
import kotlinx.android.synthetic.main.dialog_fragment_base_view.view.*
import kotlinx.android.synthetic.main.dialog_fragment_header_base_view.view.*

// bg : rounded_dialog
// Todo : update dialog -> MaterialAlertDialogBuilder(context)


/**
 *  kêt quả trả về onCreateHeaderView = null  thì ẩn header view đi ...
 *  ex : ChangeInfoDialog
 *
 *
 */

abstract class DialogFragmentBase(var cont: Context? = null) : DialogFragment() {
    private var headerView: View? = null

    interface HeaderDialog {
        fun setTitle(title: String)
        fun setLogo(@DrawableRes img: Int)
        fun getView(): View?
    }

    override fun onResume() {
        super.onResume()
        setPercentWidth()
    }

    fun setPercentWidth(percent: Double = 0.85) {
        val size = Point()
        dialog?.window?.windowManager?.defaultDisplay?.getSize(size)
        val params: WindowManager.LayoutParams? = dialog?.window?.attributes
        params?.width = (size.x * percent).toInt()
        params?.height = LinearLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogFragmentTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(context)
        return dialog.show()
    }

    /*
    * th1 : return null -> ko co header...
    * th2 : view -> header...
    *
    *  var logoImage = headerView?.findViewById<AppCompatImageView>(R.id.logo_img)
    *  var titleTextView = headerView?.findViewById<AppCompatImageView>(R.id.header_title_tv)
    * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.dialog_fragment_base_view, null)?.apply {
            background = context?.let { ContextCompat.getDrawable(it, R.drawable.rounded_dialog) }
            fun dp2px(dpValue: Float): Int {
                val scale =
                    Resources.getSystem().displayMetrics.density
                return (dpValue * scale + 0.5f).toInt()
            }
            elevation = dp2px(5f).toFloat()
            val viewContent = onCreateCustomView(
                inflater,
                container,
                savedInstanceState
            )
            content_layout?.removeAllViews()
            content_layout?.addView(viewContent)
            View.inflate(context, R.layout.dialog_fragment_header_base_view, null)
                ?.let { _headerView ->
                    val dialog = object : HeaderDialog {
                        override fun setTitle(title: String) {
                            _headerView.header_title_tv?.text = title
                        }

                        override fun setLogo(img: Int) {
                            _headerView.logo_img?.setBackgroundResource(img)
                        }

                        override fun getView(): View? {
                            return _headerView
                        }
                    }
                    headerView = onCreateHeaderView(dialog)
                    if (_headerView.id != headerView?.id) {
                        header_layout.layoutParams = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                        )
                        val layoutParam = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(0, 0, 0, 0)
                        }
                        content_layout?.layoutParams = layoutParam
                    }
                }
            headerView?.let { _headerView ->
                header_layout?.removeAllViews()
                header_layout?.addView(_headerView)
            } ?: run {
                val layoutParam = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, 0)
                }
                content_layout?.layoutParams = layoutParam
            }
        }
    }

    abstract fun onCreateHeaderView(
        header: HeaderDialog?
    ): View?

    abstract fun onCreateCustomView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?


    fun show() {
        (cont as? FragmentActivity)?.apply {
            show(supportFragmentManager, "show_dialog")
        }
    }
}
