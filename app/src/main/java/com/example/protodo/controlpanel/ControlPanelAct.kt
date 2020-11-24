package com.example.protodo.controlpanel

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.ControlPanelActBinding

class ControlPanelAct : ProTodActivity() {

    private lateinit var binding: ControlPanelActBinding

    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@ControlPanelAct, R.layout.control_panel_act)
        initView()
        initAction()
    }

    private fun initView() {

    }

    private fun initAction() {

    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, ControlPanelAct::class.java)
        }
    }
}