package com.example.protodo.palette

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import carbon.widget.CardView
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.Utils.MediaPlayer
import com.example.protodo.databinding.HolderBaseBinding
import com.example.protodo.databinding.PaletteBottomViewBinding
import com.example.protodo.recycleviewbase.HolderBase
import com.example.protodo.recycleviewbase.setUpRecycleView
import java.util.*

class PaletteBottomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: PaletteBottomViewBinding by lazy {
        DataBindingUtil.inflate<PaletteBottomViewBinding>(
            LayoutInflater.from(context),
            R.layout.palette_bottom_view,
            this,
            false
        )
    }

    init {
        removeAllViews()
        addView(binding.root)
        initView()
    }

    private fun initView() {
        initRecycleView()
    }

    private fun initRecycleView() {
        //Todo : chỉ cần truyên vào , list data , Holder :...
        val itemColor1 = ArrayList<String>()
        itemColor1.add("#FFFFFF")
        itemColor1.add("#C0C0C0")
        itemColor1.add("#808080")
        itemColor1.add("#666666")
        itemColor1.add("#FFFF00")

        val itemColor2 = ArrayList<String>()
        itemColor2.add("#000000")
        itemColor2.add("#FA9F00")
        itemColor2.add("#FF0000")
        itemColor2.add("#FF0000")
        itemColor2.add("#800000")

        val itemColor3 = ArrayList<String>()
        itemColor3.add("#FFFF00")
        itemColor3.add("#808000")
        itemColor3.add("#FFFF00")
        itemColor3.add("#00FF00")
        itemColor3.add("#008000")

        val itemColor4 = ArrayList<String>()
        itemColor4.add("#00FFFF")
        itemColor4.add("#008080")
        itemColor4.add("#0000FF")
        itemColor4.add("#000080")
        itemColor4.add("#FF00FF")

        val itemColor11 = ArrayList<String>()
        itemColor11.add("#FFFFFF")
        itemColor11.add("#C0C0C0")
        itemColor11.add("#808080")
        itemColor11.add("#666666")
        itemColor11.add("#FFFF00")

        val itemColor22 = ArrayList<String>()
        itemColor22.add("#000000")
        itemColor22.add("#FA9F00")
        itemColor22.add("#FF0000")
        itemColor22.add("#FF0000")
        itemColor22.add("#800000")

        val itemColor33 = ArrayList<String>()
        itemColor33.add("#FFFF00")
        itemColor33.add("#808000")
        itemColor33.add("#FFFF00")
        itemColor33.add("#00FF00")
        itemColor33.add("#008000")

        val itemColor44 = ArrayList<String>()
        itemColor44.add("#00FFFF")
        itemColor44.add("#008080")
        itemColor44.add("#0000FF")
        itemColor44.add("#000080")
        itemColor44.add("#FF00FF")

        val itemColor5 = ArrayList<String>()
        itemColor5.add("#800080")

        var selectedColor = itemColor1.firstOrNull()
        val listColor = mutableListOf(
            itemColor1,
            itemColor2,
            itemColor3,
            itemColor4,
            itemColor11,
            itemColor22,
            itemColor33,
            itemColor44
        )

        binding.recycleView.setUpRecycleView(
            listColor,
            HolderBase(context, R.layout.holder_base), bind = { _binding, _itemData ->
                (_binding as? HolderBaseBinding)?.apply {
                    _binding.contentLayout.forEachIndexed { _index, it ->
                        (it as? FrameLayout)?.let { _FrameLayout ->
                            (_FrameLayout.getChildAt(0) as? com.google.android.material.card.MaterialCardView)?.let { _image ->
                                _image.visibility = View.INVISIBLE
                                _image.backgroundTintList =
                                    ColorStateList.valueOf(Color.parseColor("#00000000"))
                                _image.setOnClickListener {

                                    "on Click :... ".Log()

                                    //Todo : Thanh mơ âm thanh khi nhấn nut :...
                                    MediaPlayer(context).start()

                                    val result = _itemData?.getOrNull(
                                        _index
                                    )

                                    //Todo : kêt qua khi chọn màu ...
                                    "_index :.. $result ".Log()


                                }
                            }
                        }
                    }
                    _itemData?.forEachIndexed { _index, _data ->
                        (_binding.contentLayout.getChildAt(_index) as? FrameLayout)?.let { _FrameLayout ->
                            (_FrameLayout.getChildAt(0) as? com.google.android.material.card.MaterialCardView)?.let { _image ->
                                _image.visibility = View.VISIBLE
                                _image.backgroundTintList =
                                    ColorStateList.valueOf(
                                        Color.parseColor(
                                            _itemData.getOrNull(
                                                _index
                                            )
                                        )
                                    )
                            }
                        }
                    }
                }
            })
    }
}



