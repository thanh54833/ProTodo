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

enum class RecycleViewMode {
    VERTICAL, HORIZONTAL, GRIP
}

fun <T : Any, VDB : ViewDataBinding, I : Any?> RecyclerView.setContentView(
    listData: List<T>,
    holder: HolderBase<VDB, I>,
    anim: RecycleViewAnimation? = RecycleViewAnimation.NONE,
    mode: RecycleViewMode? = RecycleViewMode.VERTICAL,
    bind: (binding: VDB, itemInfo: ItemInfo<T>?, listener: I?) -> Unit = { _, _, _ -> }
): RecyclerView.Adapter<HolderBase<VDB, I>> {
    //var animation: Animation? = null
    val recycleViewAdapter = RecycleViewBase(listData, holder, bind)

    when (anim) {
        RecycleViewAnimation.NONE -> {

        }
        RecycleViewAnimation.DOWN_TO_UP -> {
            val resId: Int = R.anim.layout_animation_down_to_up
            this.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, resId)
            //recycleViewAdapter.animationItem = AnimationUtils.loadAnimation(context, resId)
        }
        RecycleViewAnimation.UP_TO_DOWN -> {
            val resId: Int = R.anim.layout_animation_up_to_down
            this.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, resId)
            //recycleViewAdapter.animationItem = AnimationUtils.loadAnimation(context, resId)
        }
        RecycleViewAnimation.RIGHT_TO_LEFT -> {
            val resId: Int = R.anim.layout_animation_right_to_left
            this.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, resId)
            //recycleViewAdapter.animationItem = AnimationUtils.loadAnimation(context, resId)
        }
        RecycleViewAnimation.LEFT_TO_RIGHT -> {
            val resId: Int = R.anim.layout_animation_left_to_right
            this.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, resId)
            //recycleViewAdapter.animationItem = AnimationUtils.loadAnimation(context, resId)
        }
    }
    this.adapter = recycleViewAdapter
    this.itemAnimator = null

    when (mode) {
        RecycleViewMode.VERTICAL -> {
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        RecycleViewMode.HORIZONTAL -> {
            this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        RecycleViewMode.GRIP -> {

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
    var animationItem: Animation? = null

    //var listener: I? = null
    override fun onBindViewHolder(holder: HolderBase<VDB, I>, position: Int) {
        holder.apply {
            val itemInfo = ItemInfo(position = position, data = list.getOrNull(position))
            bind { _binding, _listener ->
                binds(_binding, itemInfo, _listener)
            }
        }
        //animationItem?.apply {
        setAnimation(holder.itemView, position)
        //}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBase<VDB, I> {
        return holder.onCreateViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // animation fade ...
    private fun setAnimation(
        viewToAnimate: View,
        position: Int
    ) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if ((position > lastPosition)) {//and (animationItem != null)
            // Todo : animation scale ...
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
            //Todo :

//            val ss = AnimationUtils.loadAnimation(
//                viewToAnimate.context,
//                R.anim.layout_animation_down_to_up
//            )

            viewToAnimate.startAnimation(anim)//animationItem)
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
