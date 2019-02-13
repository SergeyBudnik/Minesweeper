package com.bdev.minesweeper.ui.views.common

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.bdev.minesweeper.R
import com.bdev.minesweeper.ui.views.common.AppButtonView.AppButtonStyle.*
import kotlinx.android.synthetic.main.view_app_button.view.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EViewGroup

@EViewGroup(R.layout.view_app_button)
open class AppButtonView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private enum class AppButtonStyle(val value: Int) {
        PRIMARY(1), DEFAULT(2);

        companion object {
            fun findByValue(value: Int): AppButtonStyle {
                return values().first { it.value == value }
            }
        }
    }

    private val style: AppButtonStyle
    private val iconId: Int
    private val text: String

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.AppButtonView, 0, 0)
        try {
            style = AppButtonStyle.findByValue(ta.getInt(R.styleable.AppButtonView_app_button_style, 1))
            iconId = ta.getResourceId(R.styleable.AppButtonView_app_button_icon, 0)
            text = ta.getString(R.styleable.AppButtonView_app_button_text)
        } finally {
            ta.recycle()
        }
    }

    @AfterViews
    fun init() {
        appButtonView.setBackgroundResource(when (style) {
            PRIMARY -> R.drawable.layout_button_primary
            DEFAULT -> R.drawable.layout_button_default
        })

        appButtonTextView.text = text

        if (iconId != 0) {
            appButtonIconView.setImageDrawable(resources.getDrawable(iconId))
        } else {
            appButtonIconView.setImageDrawable(null)
        }
    }

    fun setButtonEnabled(enabled: Boolean) {
        alpha = if (enabled) { 1.0f } else { 0.6f }
    }
}
