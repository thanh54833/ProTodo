package com.example.protodo.example.exercise.dagger2

import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.DaggerActBinding
import com.example.protodo.example.exercise.dagger2.module.TestModule
import dagger.Lazy
import javax.inject.Inject

class Dagger2Act : ProTodActivity() {
    @Inject
    lateinit var firstFragment: Lazy<FirstFragment>

    @Inject
    lateinit var user: TestModule
    lateinit var binding: DaggerActBinding

    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@Dagger2Act, R.layout.dagger_act)

        supportFragmentManager.beginTransaction().add(binding.contentLayout.id, firstFragment.get()).commit()
        user.getSumUseCase().name.Log("user.getSumUseCase().name :...")

    }
}