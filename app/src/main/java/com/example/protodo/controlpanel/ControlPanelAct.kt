package com.example.protodo.controlpanel

import android.content.Context
import android.content.Intent
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
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

    override fun setupUI() {
        //TODO("Not yet implemented")
    }

    override fun observeViewModel() {
        //TODO("Not yet implemented")
    }

    private fun initView() {
        binding.soundSwt.isChecked = true
        binding.timePicker.setIs24HourView(true)

    }

    private fun initAction() {
        binding.soundGroup.setOnClickListener {
            binding.soundSwt.isChecked = !binding.soundSwt.isChecked
        }
        binding.rungGroup.setOnClickListener {
            binding.rungSwt.isChecked = !binding.rungSwt.isChecked
        }
        binding.pauseGroup.setOnClickListener {
            binding.pauseSwt.isChecked = !binding.pauseSwt.isChecked
        }

        binding.calenderBtn.setOnClickListener {
            //Todo : Thanh hiện thị màn hinh chọn ngày ra :...

        }

        //Todo :
        binding.day2.isSelected = false
        binding.day3.isSelected = false
        binding.day4.isSelected = false
        binding.day5.isSelected = false
        binding.day6.isSelected = false
        binding.day7.isSelected = false
        binding.dayC.isSelected = false

        binding.day2.setOnClickListener {
            binding.day2.changeState(!binding.day2.isSelected)
        }
        binding.day3.setOnClickListener {
            binding.day3.changeState(!binding.day3.isSelected)
        }
        binding.day4.setOnClickListener {
            binding.day4.changeState(!binding.day4.isSelected)
        }
        binding.day5.setOnClickListener {
            binding.day5.changeState(!binding.day5.isSelected)
        }
        binding.day6.setOnClickListener {
            binding.day6.changeState(!binding.day6.isSelected)
        }
        binding.day7.setOnClickListener {
            binding.day7.changeState(!binding.day7.isSelected)
        }
        binding.dayC.setOnClickListener {
            binding.dayC.changeState(!binding.dayC.isSelected)
        }


    }

    private fun AppCompatTextView.changeState(isSelected: Boolean) {
        this.isSelected = isSelected
        if (isSelected) {
            setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, ControlPanelAct::class.java)
        }
    }
}