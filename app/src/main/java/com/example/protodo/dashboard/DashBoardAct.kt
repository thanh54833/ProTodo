package com.example.protodo.dashboard

import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.AnimationItemBinding
import com.example.protodo.databinding.DashBoardActBinding
import com.example.protodo.editor.WriteAct
import com.fxn.OnBubbleClickListener
import com.halo.widget.recycle.HolderBase
import com.halo.widget.recycle.HolderListenerBase
import com.halo.widget.recycle.RecycleViewAnimation
import com.halo.widget.recycle.setContentView
import petrov.kristiyan.colorpicker.ColorPicker

class DashBoardAct : ProTodActivity() {
    lateinit var binding: DashBoardActBinding
    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(this@DashBoardAct, R.layout.dash_board_act)
        initView()
        initAction()
        initRecycleView()
    }

    private fun initAction() {
        binding.buton.setOnClickListener {

        }

        //Todo : detect change tab :...
        binding.bubbleTabBar.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                initRecycleView()
            }
        })
        binding.inboxAddBt.setOnClickListener {
            startActivity(WriteAct.getIntent(this@DashBoardAct))
        }
        binding.todayAddBt.setOnClickListener {

        }
        binding.calenderAddBt.setOnClickListener {

        }
        binding.managerAddBt.setOnClickListener {

        }
    }

    private fun initView() {

    }

    private fun initRecycleView() {
        binding.recycleView.setContentView(
            listOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
            HolderBase<AnimationItemBinding, HolderListenerBase>(
                this@DashBoardAct,
                R.layout.animation_item, object : HolderListenerBase {

                }
            ),
            RecycleViewAnimation.DOWN_TO_UP
        ) { _, _, _ ->
            "item :...".Log()
        }
    }
}