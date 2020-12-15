package com.example.protodo.abs

import android.R
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.example.protodo.Utils.Log
import com.example.protodo.common.KeyBoardModel
import com.example.protodo.example.exercise.dagger2.FirstFragment
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import javax.inject.Inject


abstract class ProTodActivity : DaggerAppCompatActivity() {
    private var keyBoardModel = KeyBoardModel()
    private var callBack: ViewTreeObserver.OnGlobalLayoutListener? = null
    private var heightOld: Int? = null
    lateinit var keyBoardActListener: (width: Int, height: Int, isVisible: Boolean) -> Unit
    //lateinit var binding: ViewDataBinding


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        //Todo : check available content view ...
        rootView?.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        initiativeView()
        if (this::keyBoardActListener.isInitialized) {
            KeyboardVisibilityEvent.setEventListener(this,
                object : KeyboardVisibilityEventListener {
                    override fun onVisibilityChanged(isOpen: Boolean) {
                        if (isOpen) {
                            keyBoardActListener(keyBoardModel.width, keyBoardModel.height, true)
                        } else {
                            keyBoardActListener(keyBoardModel.width, 0, false)
                        }
                    }
                })
            callBack = ViewTreeObserver.OnGlobalLayoutListener {
                val r = Rect()
                window.decorView.getWindowVisibleDisplayFrame(r)
                val screenHeight: Int = window.decorView.rootView.height
                val heightDifference = screenHeight - r.bottom
                if (heightDifference > 150) {
                    keyBoardModel.height = heightDifference - (heightOld ?: 0)
                    keyBoardActListener(0, keyBoardModel.height, true)
                    window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(callBack)
                } else {
                    keyBoardModel.height = 0
                    keyBoardActListener(0, keyBoardModel.height, false)
                    heightOld = heightDifference
                }
            }
            window.decorView.viewTreeObserver.addOnGlobalLayoutListener(callBack)
        }
    }

    abstract fun initiativeView()

    private val rootView: View?
        get() {
            return this.findViewById<View>(R.id.content).rootView
        }

    fun String.toast(color: Int = Color.RED) {
        val toast: Toast = Toast.makeText(baseContext, this, Toast.LENGTH_SHORT)
        val v: TextView = toast.view?.findViewById(R.id.message) as TextView
        v.setTextColor(color)
        toast.show()
    }

    companion object {
        fun getIntent() {
            "getIntent:..".Log()
        }
    }
}

