package com.example.protodo.example.exercise.firebase

import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.FireBaseActBinding

class FireBaseAct : ProTodActivity() {
    lateinit var binding: FireBaseActBinding

    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@FireBaseAct, R.layout.fire_base_act)

    }

    override fun setupUI() {
        
    }

    override fun observeViewModel() {

    }
}