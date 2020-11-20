package com.example.protodo.share

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.databinding.ShareDialogBinding
import com.example.protodo.dialog.DialogFragmentBase

class ShareDialog(context: Context) : DialogFragmentBase(context) {
    lateinit var binding: ShareDialogBinding
    private var title: String? = null
    private var content: String? = null
    private var image: String? = null
    override fun onCreateHeaderView(header: HeaderDialog?): View? {
        return null
    }
    fun setData(
        title: String? = null,
        content: String? = null,
        image: String? = null
    ): DialogFragmentBase {
        this.title = title
        this.content = content
        this.image = image
        return this@ShareDialog
    }
    override fun onCreateCustomView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.share_dialog, container, false)



        return binding.root
    }
}