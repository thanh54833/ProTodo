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


class ItemInfo<T : Any>(
    var position: Int? = null,
    var data: T? = null
)

fun <T : Any, VDB : ViewDataBinding> RecyclerView.setContentView(
    listData: List<T>,
    holder: HolderBase<VDB>,
    bind: (binding: VDB?, itemInfo: ItemInfo<T>?) -> Unit = { _, _ -> }
) {
    this.adapter = RecycleViewBase(listData, holder, bind)
    this.layoutManager = LinearLayoutManager(context)
    this.itemAnimator = null
}

class RecycleViewBase<T : Any, VDB : ViewDataBinding>(
    var list: List<T>,
    var holder: HolderBase<VDB>,
    var binds: (binding: VDB?, itemInfo: ItemInfo<T>?) -> Unit
) : RecyclerView.Adapter<HolderBase<VDB>>() {
    override fun onBindViewHolder(holder: HolderBase<VDB>, position: Int) {
        holder.apply {
            val itemInfo = ItemInfo(position = position, data = list.getOrNull(position))
            bind {
                binds(it, itemInfo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBase<VDB> {
        return holder.onCreateViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class HolderBase<VDB : ViewDataBinding>(
    var context: Context,
    var layoutId: Int
) :
    RecyclerView.ViewHolder(getView(context, layoutId)) {
    fun onCreateViewHolder(parent: ViewGroup): HolderBase<VDB> {
        return HolderBase(parent.context, layoutId)
    }

    fun bind(action: (binding: VDB?) -> Unit) {
        val binding: VDB? = DataBindingUtil.bind(itemView)
        binding?.apply {
            action(this)
        }
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


