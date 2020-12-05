package com.example.protodo.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.protodo.R
import com.example.protodo.Shortcuts.ShortCutModel
import com.example.protodo.Shortcuts.ShortcutUtils
import com.example.protodo.Test.TestViewAct
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.controlpanel.ControlPanelAct
import com.example.protodo.databinding.ActivityMainBinding
import com.example.protodo.editor.WriteAct
import java.io.IOException


class MainAct : ProTodActivity() {
    lateinit var binding: ActivityMainBinding
    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@MainAct, R.layout.activity_main)

        //Todo : room data :...
//        val wordViewModel = ViewModelProviders.of(this@MainAct).get(WordViewModel::class.java)
//        wordViewModel.insert(Word("Worker cua thanh :..."))
//        wordViewModel.allWords.observe(this, Observer { words ->
//            "works :... $words  ".Log()
//        })

        //"MainActivity :.. ".Log()

        initData()
        initView()

//        binding.contentLayout.setOnClickListener {
//            FingerPrintUtils(this@MainAct, result = { _isSuccess ->
//                "isSuccess :...  ${_isSuccess} ".Log()
//            }).authenticate()
//        }

        //initLiquidPager()
        shortCut()
        //startActivity(WriteAct.getIntent(this@MainAct))

        //Todo : chay sang man hinh test !!
        startActivity(TestViewAct.getIntent(this@MainAct))

        //startActivity(ControlPanelAct.getIntent(this@MainAct))

    }

    private fun shortCut() {
        val shortCutModel = ShortCutModel().apply {
            shortLabel = "Test Label"
            longLabel = "Test long Label"
            disabledMessage = "disabledMessage"
            icon = R.drawable.ic_app
            startIntent = TestViewAct::class.java
        }
        val shortCutModel1 = ShortCutModel().apply {
            shortLabel = "shortLabel"
            longLabel = "longLabel"
            disabledMessage = "disabledMessage"
            icon = R.drawable.ic_align_center
            startIntent = WriteAct::class.java
        }
        val shortCutModel2 = ShortCutModel().apply {
            shortLabel = "shortLabel"
            longLabel = "longLabel"
            disabledMessage = "disabledMessage"
            icon = R.drawable.ic_align_right
            startIntent = WriteAct::class.java
        }
        ShortcutUtils(this@MainAct, listOf(shortCutModel, shortCutModel1, shortCutModel2)).build()
    }

    private fun initLiquidPager() {
        binding.pager.adapter = Adapter(supportFragmentManager)
    }


    private fun <T : Fragment> displayFragment(clazz: Class<T>, initialiser: (T) -> Unit = {}): T? {
        return clazz.canonicalName?.let { className ->
            @Suppress("UNCHECKED_CAST")
            supportFragmentManager.fragmentFactory.instantiate(classLoader, className) as? T
        }?.also {
            initialiser(it)
            supportFragmentManager.beginTransaction().apply {
                replace(binding.contentLayout.id, it)
                commit()
            }
        }
    }


    @SuppressLint("CheckResult")
    private fun initView() {
        "MainActivity :.. ".Log()
        //ShareDialog(this@MainAct).setData().show()
//        binding.contentLayout.setOnClickListener {
//            ProgressBar(this@MainAct).start()
//        }
    }

    private fun initData() {
        intent.apply {
            this.extras.Log("this.extras:...")
            this.type.Log(" this.type :..")
            //getStringExtra(Intent.EXTRA_STREAM).Log("getStringExtra(Intent.EXTRA_STREAM)")
            //getStringArrayExtra(Intent.EXTRA_STREAM).Log("getStringExtra(Intent.EXTRA_STREAM)")
            //getStringArrayListExtra(Intent.EXTRA_STREAM).Log("getStringExtra(Intent.EXTRA_STREAM)")

            binding.actionImage.setImageBitmap(getPath(this))

        }
    }

    private fun getPath(data: Intent): Bitmap? {
        val imageSelected = data.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
        return try {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                imageSelected?.let {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            this.contentResolver,
                            it
                        )
                    )
                } ?: run {
                    null
                }
            } else {
                MediaStore.Images.Media.getBitmap(this.contentResolver, imageSelected ?: Uri.EMPTY)
            }
            bitmap
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}