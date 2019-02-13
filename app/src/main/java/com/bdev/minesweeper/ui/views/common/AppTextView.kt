package com.bdev.minesweeper.ui.views.common;

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.bdev.minesweeper.R

class AppTextView : AppCompatTextView {
    private enum class AppFont constructor(val index: Int, val fontName: String) {
        REGULAR(1, "fonts/sourcesanspro.ttf"),
        LIGHT(2, "fonts/sourcesansprolight.ttf"),
        BOLD(3, "fonts/sourcesansprosemibold.ttf");

        companion object {
            fun findByIndex(index: Int): AppFont {
                return values().find { it -> it.index == index } ?: throw RuntimeException()
            }
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.AppTextView, 0, 0)

        try {
            val font = AppFont.findByIndex(ta.getInt(R.styleable.AppTextView_app_font, 1))

            typeface = getTypeface(font.fontName, getContext())
        } finally {
            ta.recycle()
        }
    }

    private fun getTypeface(fontName: String, context: Context): Typeface {
        try {
            return Typeface.createFromAsset(context.assets, fontName)
        } catch (e: Exception) {
            throw RuntimeException()
        }
    }
}
