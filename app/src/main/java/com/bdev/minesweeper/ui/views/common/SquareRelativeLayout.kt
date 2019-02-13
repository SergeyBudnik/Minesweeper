package com.bdev.minesweeper.ui.views.common

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

open class SquareRelativeLayout : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
