package com.example.protodo.todo

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.protodo.R
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.BasicActBinding

class BasicAct(override val viewModel: ViewModel) : ProTodActivity() {
    private lateinit var binding: BasicActBinding

    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this, R.layout.basic_act)
        initView()
        initData()
    }

    override fun setupUI() {
        //TODO("Not yet implemented")
    }

    override fun observeViewModel() {
        //TODO("Not yet implemented")
    }

    private fun initView() {

        binding.bottomMenu.add(MeowBottomNavigation.Model(1, R.drawable.ic_button))

        binding.bottomMenu.add(MeowBottomNavigation.Model(2, R.drawable.ic_button))

        binding.bottomMenu.add(MeowBottomNavigation.Model(3, R.drawable.ic_button))

    }

    private fun initData() {

    }
}