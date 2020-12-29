package com.example.protodo.example.exercise.room

import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.protodo.R
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.RoomActBinding
import com.example.protodo.example.exercise.MVI.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class RoomAct : ProTodActivity() {

    @ExperimentalCoroutinesApi
    override val viewModel: RoomViewModel by viewModels { viewModelFactory }
    lateinit var binding: RoomActBinding

    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@RoomAct, R.layout.room_act)

    }

    override fun setupUI() {

    }

    override fun observeViewModel() {


    }

}

class RoomViewModel @javax.inject.Inject constructor() : ViewModel() {


}


