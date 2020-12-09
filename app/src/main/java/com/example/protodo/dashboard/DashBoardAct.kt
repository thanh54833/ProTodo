package com.example.protodo.dashboard

import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.AnimationItemBinding
import com.example.protodo.databinding.DashBoardActBinding
import com.halo.widget.recycle.HolderBase
import com.halo.widget.recycle.HolderListenerBase
import com.halo.widget.recycle.setContentView

class DashBoardAct : ProTodActivity() {
    lateinit var binding: DashBoardActBinding
    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@DashBoardAct, R.layout.dash_board_act)
        initView()
        initAction()
    }

    private fun initAction() {
        binding.buton.setOnClickListener {
            binding.recycleView.setContentView(
                listOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                HolderBase<AnimationItemBinding, HolderListenerBase>(
                    this@DashBoardAct,
                    R.layout.animation_item, object : HolderListenerBase {

                    }
                )
            ) { _, _, _ ->
                "item :...".Log()

            }
        }
    }

    private fun initView() {

    }
}