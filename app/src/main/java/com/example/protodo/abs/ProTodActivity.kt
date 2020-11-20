package com.example.protodo.abs

import android.graphics.Rect
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.example.protodo.Utils.Log
import com.example.protodo.common.KeyBoardModel
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener


abstract class ProTodActivity : AppCompatActivity() {
    private var keyBoardModel = KeyBoardModel()
    private var callBack: ViewTreeObserver.OnGlobalLayoutListener? = null
    private var heightOld: Int? = null
    lateinit var keyBoardActListener: (width: Int, height: Int, isVisible: Boolean) -> Unit

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
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

    companion object {
        fun getIntent() {
            "getIntent:..".Log()
        }
    }
}

