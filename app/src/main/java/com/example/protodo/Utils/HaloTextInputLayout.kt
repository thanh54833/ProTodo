/*
 * Copyright 10/10/2018 Hahalolo. All rights reserved.
 *
 * https://help.hahalolo.com/commercial_terms/
 */

package com.example.protodo.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.protodo.R
import com.example.protodo.common.SizeUtils
import com.google.android.material.textfield.TextInputLayout

interface Interaction {
    fun setErrorLabel(message: String)
}

@Suppress("CAST_NEVER_SUCCEEDS")
class HaloTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.Halo_TextInputLayout
) : TextInputLayout(context, attrs, defStyleAttr) {
    //, Interaction
    private var _editText: AppCompatEditText? = null
    private var textWatcher: TextWatcher? = null
    private var autoComplete: AutoCompleteTextView? = null
    private var hasRequiredHelper: Boolean = false
    private var requiredText: String = ""

    private var paddingTopNew: Int = paddingTop
    private var paddingBottomNew: Int = paddingBottom
    private var onFocusListener: (isFocus: Boolean) -> Unit = {}

    var isFocus: Boolean = true
        set(value) {
            setFocusEnable(isFocus)
            field = value
        }

    var isTouchMode: Boolean = true
        set(value) {
            isFocusableInTouchMode = value
            field = value
        }

    var isCursorVisible: Boolean = true
        set(value) {
            setFocusEnable(isFocus)
            field = value
        }

    private var isLongClick: Boolean? = null
        set(value) {
            setFocusEnable(isFocus)
            field = value
        }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initView()
    }

    private fun initView() {
        (editText as? AppCompatEditText)?.apply {
            _editText = this//editText as? HaloEditText
            setBackgroundColor(Color.WHITE)

            //Todo : thanh fix later ...
            paddingTopNew = SizeUtils.dp2px(0f)//.Log("paddingTopNew :...")//paddingTop//
            paddingBottomNew = SizeUtils.dp2px(0f)//.Log("paddingBottomNew :...")//paddingBottom//

            setPadding(0, paddingTopNew, 0, paddingBottomNew)
            textSize = 15f
            setErrorTextColor(ContextCompat.getColorStateList(context, R.color.text_notice))
            gravity = Gravity.BOTTOM

            _editText?.apply {
                isFocusableInTouchMode = true
                setFocusEnable(isFocus)
                setTextColor(
                    ContextCompat.getColorStateList(context, R.color.black)
                )
                if (inputType == 1) {
                    inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                }
                background.setTint(ContextCompat.getColor(context, R.color.transparent))
                setHintTextColor(ContextCompat.getColorStateList(context, R.color.text_light))
                //Todo : thanh fix later ...
                val editPaddingTop = SizeUtils.dp2px(30f)//.Log("editPaddingTop :...")
                val editPaddingBottom = SizeUtils.dp2px(5f)//.Log("editPaddingBottom :...")
                setPadding(0, editPaddingTop, 0, editPaddingBottom)
            }
            _editText?.setOnFocusChangeListener { _, _hasFocus ->
                if (_hasFocus) {
                    _editText?.clearError()
                    hasFocusEditText()
                } else {
                    lostFocusEdieText()
                    if (TextUtils.isEmpty(_editText?.text) and (!TextUtils.isEmpty(requiredText))) {
                        setRequiredHelperV2(requiredText)
                    }
                }
            }
        }
        // Todo Mode : DISABLE_LABEL, SS_SS
        (editText as? AutoCompleteTextView)?.apply {
            autoComplete = this

            setBackgroundColor(Color.WHITE)
            setTextColor(
                ContextCompat.getColorStateList(context, R.color.black)
            )
            background.setTint(ContextCompat.getColor(context, R.color.transparent))
            setHintTextColor(ContextCompat.getColor(context, R.color.text_light))

            val editPaddingTop = SizeUtils.dp2px(30f)//.Log("editPaddingTop :...")
            val editPaddingBottom = SizeUtils.dp2px(5f)//.Log("editPaddingBottom :...")

            if (contentDescription == "DISABLE_LABEL") {
                setPadding(0, paddingTop, 0, paddingBottom)
            } else {
                setPadding(0, editPaddingTop, 0, editPaddingBottom)
            }

            isFocusable = false
            autoComplete?.setOnFocusChangeListener { _, _hasFocus ->
                if (_hasFocus) {
                    hasFocusEditText()
                } else {
                    lostFocusEdieText()
                }
            }
        }
    }

    fun setOnFocusListener(onFocusListener: (isFocus: Boolean) -> Unit) {
        this.onFocusListener = onFocusListener
    }


    fun setFocusEnable(isFocus: Boolean) {
        _editText?.apply {
            isCursorVisible = isFocus
            isFocusable = isFocus
            isLongClickable = isLongClick ?: isFocus
        }
        (editText as? AutoCompleteTextView)?.apply {
            isFocusable = isFocus
        }
    }

    private fun View.clearError() {
        (this as? AppCompatEditText)?.apply {
            if (isErrorEnabled) {
                isErrorEnabled = false
                error = null
            }
            if (this@HaloTextInputLayout.isHelperTextEnabled and hasRequiredHelper) {
                this@HaloTextInputLayout.isHelperTextEnabled = false
            }
        }
    }

    fun clearError() {
        (editText as? AppCompatEditText)?.apply {
            if (isErrorEnabled) {
                isErrorEnabled = false
                error = null
            }
        }
    }

    fun setOnClick() {
        (editText as? AppCompatEditText)?.apply {
            setOnFocusChangeListener { view, b ->


            }
        }
    }

    private fun hasFocusEditText() {
        this.setHintTextAppearance(R.style.Action)
        onFocusListener(true)
    }

    private fun lostFocusEdieText() {
        this.setHintTextAppearance(R.style.InAction)
        onFocusListener(false)
    }

    private fun setUpHelper() {
        this.setHelperTextColor(
            ContextCompat.getColorStateList(
                context,
                R.color.text_notice
            )
        )
    }

    fun setEditActionListener(errorMessage: (textChanged: String) -> String?) {
        (editText as? AppCompatEditText)?.let { _editText ->
            textWatcher = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(textChange: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (TextUtils.isEmpty(textChange)) {
                        errorMessage(textChange.toString())?.let { setRequiredHelperV2(it) }
                    } else if (!TextUtils.isEmpty(errorMessage(textChange.toString()))) {
                        errorMessage(textChange.toString())?.let { setTextError(it) }
                    } else {
                        _editText.clearError()
                    }
                }
            }
            _editText.addTextChangedListener(textWatcher)
        }
    }

    fun setRequiredInputField(errorEmpty: String, errorMessage: (textChanged: String) -> String?) {
        (editText as? AppCompatEditText)?.let { _editText ->

            if (TextUtils.isEmpty(_editText.text)) {
                setRequiredHelperV2(errorEmpty)
            }
            textWatcher = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(textChange: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (TextUtils.isEmpty(textChange)) {
                        setTextError(errorEmpty)
                    } else if (!TextUtils.isEmpty(errorMessage(textChange.toString()))) {
                        errorMessage(textChange.toString())?.let { setTextError(it) }
                    } else {
                        _editText.clearError()
                    }
                }
            }
            _editText.addTextChangedListener(textWatcher)
        }
    }

    fun setRequiredInput(
        errorEmpty: String,
        result: (textChange: String?, message: Interact) -> Interact
    ) {
        (editText as? AppCompatEditText)?.let { _editText ->
            if (TextUtils.isEmpty(_editText.text)) {
                setRequiredHelperV2(errorEmpty)
            }
            textWatcher = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(textChange: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val interact = Interact()
                    when {
                        TextUtils.isEmpty(textChange) -> {
                            setTextError(errorEmpty)
                        }
                        result(textChange.toString(), interact).isError -> {
                            setTextError(result(textChange.toString(), interact).getLabelError())
                        }
                        else -> {
                            _editText.clearError()
                        }
                    }
                }
            }
            _editText.addTextChangedListener(textWatcher)
        }
    }


    fun setRequiredInputField(textError: String) {
        (editText as? AppCompatEditText)?.let { _editText ->
            if (TextUtils.isEmpty(_editText.text)) {
                setRequiredHelperV2(textError)
            }
            textWatcher = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (TextUtils.isEmpty(p0.toString())) {
                        setTextError(textError)
                    } else {
                        _editText.clearError()
                    }
                }
            }
            _editText.addTextChangedListener(textWatcher)
        }
    }


    fun setRequiredInput(textError: String) {
        (editText as? AppCompatEditText)?.let { _editText ->
            if (TextUtils.isEmpty(_editText.text)) {
                setRequiredHelperV2(textError)
            }
            textWatcher = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (TextUtils.isEmpty(p0.toString())) {
                        setTextError(textError)
                    } else {
                        _editText.clearError()
                    }
                }
            }
            _editText.addTextChangedListener(textWatcher)
        }
    }

    fun setRequiredHelperV2(message: String) {
        requiredText = message
        setUpHelper()
        setHelperMessage(message)
        if (!hasRequiredHelper) {
            hasRequiredHelper = true
        }
    }

    fun setError(error: String) {
        (editText as? AppCompatEditText)?.let { _editText ->
            if (!TextUtils.isEmpty(error)) {
                setTextError(error)
                lostFocusEdieText()
            }
        }
    }


    fun setHelperMessage(text: String) {
        if (!hasRequiredHelper) {
            (editText as? AppCompatEditText)?.apply {
                if (!TextUtils.isEmpty(text)) {
                    this@HaloTextInputLayout.setHelperTextTextAppearance(R.style.HelperTextTextAppearance)
                    this@HaloTextInputLayout.helperText = text
                }
            }
        }
    }

    //Todo : using style : style="@style/Halo.TextInputLayout.dropdown" for TextInputLayout
    fun setDataDropdown(list: MutableList<String>?, result: (index: Int) -> Unit) {
        (list as? List<String>)?.let { _list ->
            val adapter = ArrayAdapter(context, R.layout.list_item_drop_down, _list)
            (editText as? AutoCompleteTextView)?.apply {
                setText(_list.firstOrNull().toString())
                setAdapter(adapter)
                setOnItemClickListener { _, _, _index, _ ->
                    result(_index)
                }
            }
        }
    }

    //Todo : using style : style="@style/Halo.TextInputLayout.dropdown" for TextInputLayout
    fun <T> setDataDropdown(
        list: MutableList<T>,
        getContentItem: (element: T) -> String,
        result: (text: String, position: Int, data: T) -> Unit,
        itemSelect: Int = 0
    ) {
        val listItems = mutableListOf<String>()
        list.forEach { _element ->
            listItems.add(getContentItem(_element))
        }

        (editText as? AutoCompleteTextView)?.apply {
            setText(listItems.firstOrNull().toString())
            setText(listItems.getOrNull(itemSelect))

            fun handleOnCLick() {
                val adapter = object :
                    ArrayAdapter<String>(context, R.layout.list_item, R.id.text_view, listItems) {
                    override fun getView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val view = parent.let { super.getView(position, convertView, it) }
                        val itemTextView = view.findViewById<AppCompatTextView>(R.id.text_view)
                        val dividerView = view.findViewById<View>(R.id.divider_list_item)
                        itemTextView.text = listItems.getOrNull(position)
//                        "list_item :...position: $position  (listItems.size - 1): ${(listItems.size - 1)} ".Log()
//                        if (position == (listItems.size - 1)) {
//                            dividerView.visibility = View.GONE
//                        } else {
//                            dividerView.visibility = View.VISIBLE
//                        }
                        return view
                    }
                }
                setAdapter(adapter)
                setOnItemClickListener { _, _, _index, _ ->
                    listItems.getOrNull(_index)?.let { _textSelected ->
                        setText(_textSelected)
                        result(_textSelected, _index, list.getOrNull(_index)!!)
                    }
                }
                showDropDown()
            }
            setOnClickListener {
                handleOnCLick()
            }
//            setEndIconOnClickListener {
//                handleOnCLick()
//            }

        }
    }

    // using style : style="@style/Halo.TextInputLayout.dropdown" for TextInputLayout
    @SuppressLint("SetTextI18n")
    fun <T> setSpanDropdown(
        list: MutableList<T>,
        getContentItem: (element: T) -> SpannableStringBuilder,
        result: (text: String, position: Int, data: T) -> Unit,
        itemSelect: Int = 0,
        onClick: () -> Unit = {}
    ) {
        val listItems = mutableListOf<SpannableStringBuilder>()
        list.forEach { _element ->
            listItems.add(getContentItem(_element))
        }
        (editText as? AutoCompleteTextView)?.apply {
            //setText("   " + listItems.getOrNull(itemSelect))
            text = listItems.getOrNull(itemSelect)
            fun handleOnCLick() {
                val adapter = object : ArrayAdapter<SpannableStringBuilder>(
                    context,
                    R.layout.list_item_drop_down,
                    R.id.text_view,
                    listItems
                ) {
                    override fun getView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val view = parent.let { super.getView(position, convertView, it) }
                        val itemTextView = view.findViewById<AppCompatTextView>(R.id.text_view)
                        // val dividerView = view.findViewById<View>(R.id.divider_list_item)
                        itemTextView.text = listItems.getOrNull(position)
//                        if (position == (listItems.size - 1)) {
//                            dividerView.visibility = View.GONE
//                        }
                        return view
                    }
                }
                setAdapter(adapter)
                setOnItemClickListener { _, _, position, _ ->
                    listItems.getOrNull(position)?.let { _itemSelected ->
                        text = _itemSelected
                        result(_itemSelected.toString(), position, list.getOrNull(position)!!)
                    }
                }
                showDropDown()
            }
            setOnClickListener {
                handleOnCLick()
                onClick()
            }
//            setEndIconOnClickListener {
//                handleOnCLick()
//                onClick()
//            }
        }
    }

    private fun setTextError(message: String) {
        //setRequiredHelperV2(message)
        this@HaloTextInputLayout.error = message
    }

//    fun setSuffixText(message: String) {
//        suffixText = message
//    }

    fun setRequiredInputLabel(label: String) {

    }
}


class Interact(private var error: String? = "", var isError: Boolean = false) {
    fun setLabelError(error: String) {
        this.isError = true
        this.error = error
    }

    fun getLabelError(): String {
        return error ?: ""
    }
}

