package com.example.protodo.recycleviewbase

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemInfo<T : Any>(
    var position: Int? = null,
    var data: T? = null
)

interface HolderBaseListener {
}

fun <T : Any, VDB : ViewDataBinding, I : Any?> RecyclerView.setContentView(
    listData: List<T>,
    holder: HolderBase<VDB, I>,
    bind: (binding: VDB, itemInfo: ItemInfo<T>?, listener: I?) -> Unit = { _, _, _ -> }
): RecyclerView.Adapter<HolderBase<VDB, I>> {
    val recycleViewAdapter = RecycleViewBase(listData, holder, bind)
    this.adapter = recycleViewAdapter
    this.layoutManager = LinearLayoutManager(context)
    this.itemAnimator = null
    return recycleViewAdapter
}

class RecycleViewBase<T : Any, VDB : ViewDataBinding, I : Any?>(
    var list: List<T>,
    var holder: HolderBase<VDB, I>,
    var binds: (binding: VDB, itemInfo: ItemInfo<T>?, interact: I?) -> Unit
) : RecyclerView.Adapter<HolderBase<VDB, I>>() {
    //var listener: I? = null
    override fun onBindViewHolder(holder: HolderBase<VDB, I>, position: Int) {
        holder.apply {
            val itemInfo = ItemInfo(position = position, data = list.getOrNull(position))
            bind { _binding, _listener ->
                binds(_binding, itemInfo, _listener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBase<VDB, I> {
        return holder.onCreateViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class HolderBase<VDB : ViewDataBinding, I : Any?>(
    var context: Context,
    var layoutId: Int,
    var listener: I? = null
) :
    RecyclerView.ViewHolder(getView(context, layoutId)) {
    fun onCreateViewHolder(parent: ViewGroup): HolderBase<VDB, I> {
        return HolderBase(parent.context, layoutId, listener)
    }

    fun bind(action: (binding: VDB, listener: I?) -> Unit) {
        val binding: VDB? = DataBindingUtil.bind(itemView)
        binding?.let { _binding ->
            listener?.let { _listener ->
                action(_binding, _listener)
            }
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

