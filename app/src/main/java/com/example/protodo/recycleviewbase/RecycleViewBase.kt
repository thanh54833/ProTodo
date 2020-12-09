package com.halo.widget.recycle


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.view.animation.ScaleAnimation
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.protodo.R
import java.util.*


class ItemInfo<T : Any>(
    var position: Int? = null,
    var data: T? = null
)

enum class RecycleViewAnimation {
    NONE, DOWN_TO_UP, UP_TO_DOWN, RIGHT_TO_LEFT, LEFT_TO_RIGHT
}

fun <T : Any, VDB : ViewDataBinding, I : Any?> RecyclerView.setContentView(
    listData: List<T>,
    holder: HolderBase<VDB, I>,
    item: Int? = 0,
    bind: (binding: VDB, itemInfo: ItemInfo<T>?, listener: I?) -> Unit = { _, _, _ -> }
): RecyclerView.Adapter<HolderBase<VDB, I>> {
    val recycleViewAdapter = RecycleViewBase(listData, holder, bind)
    this.adapter = recycleViewAdapter
    this.layoutManager = LinearLayoutManager(context)
    this.itemAnimator = null

    val anim: RecycleViewAnimation? = RecycleViewAnimation.DOWN_TO_UP

    when (anim) {
        RecycleViewAnimation.NONE -> {

        }
        RecycleViewAnimation.DOWN_TO_UP -> {
            val resId: Int = R.anim.layout_animation_down_to_up
            val animation: LayoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context, resId)
            this.layoutAnimation = animation
        }
        RecycleViewAnimation.UP_TO_DOWN -> {
            val resId: Int = R.anim.layout_animation_up_to_down
            val animation: LayoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context, resId)
            this.layoutAnimation = animation
        }
        RecycleViewAnimation.RIGHT_TO_LEFT -> {
            val resId: Int = R.anim.layout_animation_right_to_left
            val animation: LayoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context, resId)
            this.layoutAnimation = animation
        }
        RecycleViewAnimation.LEFT_TO_RIGHT -> {
            val resId: Int = R.anim.layout_animation_left_to_right
            val animation: LayoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context, resId)
            this.layoutAnimation = animation
        }
    }



    return recycleViewAdapter
}

class RecycleViewBase<T : Any, VDB : ViewDataBinding, I : Any?>(
    var list: List<T>,
    var holder: HolderBase<VDB, I>,
    var binds: (binding: VDB, itemInfo: ItemInfo<T>?, interact: I?) -> Unit
) : RecyclerView.Adapter<HolderBase<VDB, I>>() {
    private var lastPosition = -1

    //var listener: I? = null
    override fun onBindViewHolder(holder: HolderBase<VDB, I>, position: Int) {
        holder.apply {
            val itemInfo = ItemInfo(position = position, data = list.getOrNull(position))
            bind { _binding, _listener ->
                binds(_binding, itemInfo, _listener)
            }
        }
        //setAnimation(holder.itemView, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBase<VDB, I> {
        return holder.onCreateViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // animation fade ...
    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val anim = ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            anim.duration = Random().nextInt(501).toLong()
            //to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim)
            lastPosition = position
        }
    }
}

class HolderBase<VDB : ViewDataBinding, I : Any?>(
    var context: Context,
    var layoutId: Int,
    var listener: I?
) :
    RecyclerView.ViewHolder(getView(context, layoutId)) {
    fun onCreateViewHolder(parent: ViewGroup): HolderBase<VDB, I> {
        return HolderBase(parent.context, layoutId, listener)
    }

    fun bind(action: (binding: VDB, listener: I?) -> Unit) {
        val binding: VDB? = DataBindingUtil.bind(itemView)
        binding?.let { _binding ->
            action(_binding, listener)
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

interface HolderListenerBase {

}
