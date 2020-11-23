package com.example.protodo.recycleviewbase

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.protodo.R

fun <T> RecyclerView.setUpRecycleView(
    listData: List<T>,
    holder: HolderBase,
    bind: (binding: ViewDataBinding?, itemData: T?) -> Unit = { _, _ -> }
) {
    this.adapter = RecycleViewBase(listData, holder, bind)
    this.layoutManager = LinearLayoutManager(context)
    this.itemAnimator = null
}

class RecycleViewBase<T>(
    var list: List<T>,
    var holder: HolderBase,
    var binds: (binding: ViewDataBinding?, itemData: T?) -> Unit
) : RecyclerView.Adapter<HolderBase>() {
    override fun onBindViewHolder(holder: HolderBase, position: Int) {
        holder.apply {
            val itemData = list.getOrNull(position)
            bind {
                binds(it, itemData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBase {
        return holder.onCreateViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_base
    }
}

class HolderBase(var context: Context, var layoutId: Int) :
    RecyclerView.ViewHolder(getView(context, layoutId)) {
    var binding: ViewDataBinding? = null
    fun onCreateViewHolder(parent: ViewGroup): HolderBase {
        return HolderBase(parent.context, layoutId)
    }

    fun bind(action: (binding: ViewDataBinding?) -> Unit) {
        binding = DataBindingUtil.bind(itemView)
        action(binding)
        binding?.executePendingBindings()
    }
}

fun getView(context: Context, layoutId: Int): View {
    return View.inflate(context, layoutId, null)?.apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }!!
}

