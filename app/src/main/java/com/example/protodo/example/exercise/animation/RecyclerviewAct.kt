package com.example.protodo.example.exercise.animation

import androidx.databinding.DataBindingUtil
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.abs.ProTodActivity
import com.example.protodo.databinding.AnimationItemBinding
import com.example.protodo.databinding.RecyclerviewActBinding
import com.halo.widget.recycle.HolderBase
import com.halo.widget.recycle.HolderListenerBase
import com.halo.widget.recycle.setContentView


class RecyclerviewAct : ProTodActivity() {
    lateinit var binding: RecyclerviewActBinding

    override fun initiativeView() {
        binding = DataBindingUtil.setContentView(
            this@RecyclerviewAct,
            R.layout.recyclerview_act
        )
        intRecycleView()
        initAction()
    }

    private fun initAction() {
        binding.buton.setOnClickListener {
            binding.recycleView.setContentView(
                listOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                HolderBase<AnimationItemBinding, HolderListenerBase>(
                    this@RecyclerviewAct,
                    R.layout.animation_item, object : HolderListenerBase {

                    }
                )
            ) { _, _, _ ->
                "item :...".Log()

            }
        }
    }

    private fun intRecycleView() {

    }


}