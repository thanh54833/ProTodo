package com.example.protodo.editor

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.SettingUtils.SettingUtils
import com.example.protodo.Shortcuts.ShortCutModel
import com.example.protodo.Shortcuts.ShortcutUtils
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.common.SizeUtils
import com.example.protodo.common.SizeUtils.getViewHeight
import com.example.protodo.databinding.WriteActBinding
import com.example.protodo.effect.AnimationUtils
import com.example.protodo.typeface.Font
import com.example.protodo.typeface.TypefaceUtils
import kotlinx.android.synthetic.main.expand_option_type.view.*
import petrov.kristiyan.colorpicker.ColorPicker
import java.util.*


class WriteAct : ProTodActivity() {
    lateinit var binding: WriteActBinding
    private var expandView: View? = null
    private var isExpand: Boolean = false

    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@WriteAct, R.layout.write_act)
        initData()
        initView()
        initAction()
        initKeyboard()
        handleContent()

        "turn off :... ".Log()

//        val wifi: WifiManager =
//            applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//        wifi.isWifiEnabled = false


//        val wifiManager = baseContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//        wifiManager.isWifiEnabled = true

        //Todo :check wifi on/off
        this.registerReceiver(
            wifiStateChangedReceiver,
            IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        )
        binding.contentTv.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val panelIntent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                startActivityForResult(panelIntent, 0)
            } else {
                // add appropriate permissions to AndroidManifest file (see https://stackoverflow.com/questions/3930990/android-how-to-enable-disable-wifi-or-internet-connection-programmatically/61289575)
                (baseContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager)?.apply {
                    isWifiEnabled = true /*or false*/
                }
            }
        }

        SettingUtils(this@WriteAct).apply {

            //clearAppData(applicationContext.packageName)
            clearAllApp()
            //appInForeground()
            //isRunning(this@WriteAct, "com.android.chrome").Log("com.android.chrome :..")

            this@WriteAct.isAppInForeground()


            //clearAppData("clearAppData")

        }
    }

    fun Context.isAppInForeground(): Boolean {

        val application = this.applicationContext
        val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcessList = activityManager.runningAppProcesses

        if (runningProcessList != null) {
            val myApp = runningProcessList.find { it.processName == application.packageName }
            runningProcessList.forEach { _item ->
                "runningProcessList :... ${_item.processName} ".Log()
            }
            ActivityManager.getMyMemoryState(myApp)
            return myApp?.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
        }
        return false
    }


    private val wifiStateChangedReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            // TODO Auto-generated method stub
            val extraWifiState = intent.getIntExtra(
                WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN
            )
            when (extraWifiState) {
                WifiManager.WIFI_STATE_DISABLED -> "WIFI STATE DISABLED".Log()
                WifiManager.WIFI_STATE_DISABLING -> "WIFI STATE DISABLING".Log()
                WifiManager.WIFI_STATE_ENABLED -> "WIFI STATE ENABLED".Log()
                WifiManager.WIFI_STATE_ENABLING -> "WIFI STATE ENABLING".Log()
                WifiManager.WIFI_STATE_UNKNOWN -> "WIFI STATE UNKNOWN".Log()
            }
        }
    }


    fun shortCut() {
        val shortCutModel = ShortCutModel().apply {
            shortLabel = "shortLabel"
            longLabel = "longLabel"
            disabledMessage = "disabledMessage"
            icon = R.drawable.ic_app
            startIntent = WriteAct::class.java
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
        ShortcutUtils(this@WriteAct, listOf(shortCutModel, shortCutModel1, shortCutModel2)).build()
    }


    //Todo :
    private fun handleContent() {

        //val font = Typeface.createFromAsset(assets, "Akshar.ttf")
        var mStart = -1
        binding.contentTv.setOnClickListener {
            mStart = binding.contentTv.text?.length ?: -1
        }

        binding.contentTv.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(textChange: Editable?) {
                //"afterTextChanged:...${textChange} ".Log()
            }

            override fun beforeTextChanged(textChange: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //"beforeTextChanged:...${textChange} ".Log()
                handleBeforeContentView()
            }

            override fun onTextChanged(
                textChange: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                handleContentView()
            }
        })
    }

    fun handleBeforeContentView() {
        when {
            Font.Bold.isAction -> {
                Font.Bold.start = binding.contentTv.length()
            }
            Font.Bold.isAction -> {
                Font.Italic.start = binding.contentTv.length()
            }
            Font.Underline.isAction -> {
                Font.Underline.start = binding.contentTv.length()
            }
        }
    }

    fun handleContentView() {
        val fontNormal = TypefaceUtils.getNormal()
        //binding.contentTv.paintFlags = binding.contentTv.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        if (Font.Bold.isAction) {
            val start = Font.Bold.start.Log("Font.Bold.start :...")
            val end = binding.contentTv.length().Log("binding.contentTv.length() :...")
            if (end >= start) {
                binding.contentTv.text?.setSpan(
                    TypefaceUtils.getBold(), start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        if (Font.Italic.isAction) {
            val start = Font.Italic.start.Log("Font.Italic.start :...")
            val end = binding.contentTv.length().Log("binding.contentTv.length() :...")
            if (end >= start) {
                binding.contentTv.text?.setSpan(
                    TypefaceUtils.getItalic(), start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        if (Font.Underline.isAction) {
            val start = Font.Underline.start.Log("Font.Italic.start :...")
            val end = binding.contentTv.length().Log("binding.contentTv.length() :...")
            if (end >= start) {
                binding.contentTv.text?.setSpan(
                    TypefaceUtils.getUnderline(), start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }


//        if ((binding.contentTv.text?.length ?: 0) < 5) {
//
//        } else {
//            val end: Int = binding.contentTv.text?.length ?: 0
//            binding.contentTv.text?.setSpan(
//                fontBold, 5,
//                end,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        }

    }

    private fun initKeyboard() {
        keyBoardActListener = { _, _height, _ ->
            binding.keyboardGroup.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                _height
            )
        }
    }

    private fun initData() {
    }

    private fun initView() {
    }

    @SuppressLint("InflateParams")
    private fun initAction() {
        //Todo : tool bar listener :...
        binding.doneBt.setOnClickListener {

        }
        binding.turnLeft.setOnClickListener {

        }
        binding.turnRight.setOnClickListener {

        }
        binding.fullScreen.setOnClickListener {

        }
        binding.findBt.setOnClickListener {

        }

        // Todo : Bottom menu listener :...

        binding.boldBt.setOnClickListener {
            binding.boldBt.isSelected = !binding.boldBt.isSelected
            binding.boldBt.handleBgBt()
            Font.Bold.isAction = binding.boldBt.isSelected
        }
        binding.italicBt.setOnClickListener {
            binding.italicBt.isSelected = !binding.italicBt.isSelected
            binding.italicBt.handleBgBt()
            Font.Italic.isAction = binding.italicBt.isSelected
        }
        binding.underlinedBt.setOnClickListener {
            binding.underlinedBt.isSelected = !binding.underlinedBt.isSelected
            binding.underlinedBt.handleBgBt()
            Font.Underline.isAction = binding.underlinedBt.isSelected
        }

        //Todo : thanh chưa viết xong , trương hợp scroll ...
        binding.alignBt.setOnClickListener {
            binding.alignBt.isSelected = !binding.alignBt.isSelected
            binding.alignBt.handleBgBt()

            val inflater =
                this@WriteAct.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val menuView = inflater.inflate(R.layout.menu_align_view, null, false)
            val pw = PopupWindow(
                menuView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
            )

            // The code below assumes that the root container has an id called 'main'
            val diff = binding.alignBt.x.toInt() - (SizeUtils.getViewWidth(menuView) / 2)
            val x = if (diff > 0) diff else 0
            val y = getViewHeight(menuView) + 200

            pw.showAtLocation(
                binding.alignBt,
                Gravity.BOTTOM or Gravity.START,
                x,
                y
            )
        }


        fun picketColor(title: String) {
            //Todo : show dialog pick color ...
            val colors = ArrayList<String>()
            colors.add("#FFFFFF")
            colors.add("#C0C0C0")
            colors.add("#808080")
            colors.add("#666666")
            colors.add("#FFFF00")
            colors.add("#000000")
            colors.add("#FA9F00")
            colors.add("#FF0000")
            colors.add("#FF0000")
            colors.add("#800000")
            colors.add("#FFFF00")
            colors.add("#808000")
            colors.add("#FFFF00")
            colors.add("#00FF00")
            colors.add("#008000")
            colors.add("#00FFFF")
            colors.add("#008080")
            colors.add("#0000FF")
            colors.add("#000080")
            colors.add("#FF00FF")
            colors.add("#800080")

            val colorPicker = ColorPicker(this@WriteAct)
            colorPicker.setDefaultColorButton(Color.parseColor("#f84c44"))

            colorPicker.setOnChooseColorListener(object : ColorPicker.OnChooseColorListener {
                override fun onChooseColor(position: Int, color: Int) {
                    // put code
                }

                override fun onCancel() {
                    // put code
                }
            }).addListenerButton("Chọn") { v, position, color ->
                // put code
                colorPicker.dismissDialog()
            }.disableDefaultButtons(true)
                .setColumns(6)
                .setColors(colors)
                .setTitle(title)
                .setRoundColorButton(true)
            colorPicker.dialogBaseLayout?.let { _contentView ->
                _contentView.background =
                    ContextCompat.getDrawable(this@WriteAct, R.drawable.rounded_dialog)
                _contentView.gravity = Gravity.CENTER
                _contentView.findViewById<AppCompatTextView>(R.id.title)?.let { _titleTv ->
                    _contentView.layoutParams = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                    )
                    _titleTv.setTextAppearance(this@WriteAct, R.style.ProTodo_TextView_Normal)
                    _titleTv.setTextColor(
                        ContextCompat.getColorStateList(
                            this@WriteAct,
                            R.color.black
                        )
                    )
                    _titleTv.textSize = SizeUtils.px2dp(this@WriteAct, 45f).toFloat()
                }
                _contentView.findViewById<LinearLayout>(R.id.buttons_layout)?.let { _bottomView ->
                    (_bottomView.getChildAt(2) as? Button)?.let { _positive ->
                        _positive.isAllCaps = false
                        _positive.text = _positive.text.toString().toLowerCase().capitalize()
                        _positive.textSize = SizeUtils.px2dp(this@WriteAct, 40f).toFloat()
                        _positive.setTextColor(
                            ContextCompat.getColorStateList(
                                this@WriteAct,
                                R.color.colorPrimary
                            )
                        )
                    }
                }
            }

            colorPicker.show()
            val dialog = colorPicker.getmDialog()
            val size = Point()
            dialog?.window?.windowManager?.defaultDisplay?.getSize(size)
            val params: WindowManager.LayoutParams? = dialog?.window?.attributes
            params?.width = (size.x * 0.85).toInt()
            params?.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.attributes = params as WindowManager.LayoutParams
        }

        binding.textColorBt.setOnClickListener {
            picketColor("Chọn màu phông chữ")
        }
        binding.textPaletteBt.setOnClickListener {
            picketColor("Chọn màu đánh dấu")
        }


        binding.moreBt.setOnClickListener {
            expandView = View.inflate(this@WriteAct, R.layout.expand_option_type, null).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            }.apply {
                over_layout?.setOnClickListener {
                    //"over_layout?.setOnClickListener :...".Log()
                    AnimationUtils.collapse(binding.expandLayout)
                }
                camera_bt?.setOnClickListener {
                    AnimationUtils.collapse(binding.expandLayout)
                }
                picture_bt?.setOnClickListener {
                    AnimationUtils.collapse(binding.expandLayout)
                }
                palette_bt?.setOnClickListener {
                    AnimationUtils.collapse(binding.expandLayout)
                }
                record_bt?.setOnClickListener {
                    AnimationUtils.collapse(binding.expandLayout)
                }
            }
            binding.expandLayout.addView(expandView)
            //AnimationUtils.collapse(binding.expandLayout)
            isExpand = if (!isExpand) {
                AnimationUtils.expand(
                    binding.expandLayout,
                    targetHeight = getViewHeight(binding.expandLayout)
                )
                true
            } else {
                AnimationUtils.collapse(binding.expandLayout)
                false
            }
        }


    }

    private fun AppCompatImageView.handleBgBt() {
        backgroundTintList = if (isSelected) {
            ContextCompat.getColorStateList(this@WriteAct, R.color.black)
        } else {
            ContextCompat.getColorStateList(this@WriteAct, R.color.divider)
        }
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, WriteAct::class.java)
        }
    }
}