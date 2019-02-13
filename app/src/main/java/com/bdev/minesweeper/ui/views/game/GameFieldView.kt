package com.bdev.minesweeper.ui.views.game

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import com.bdev.minesweeper.R
import kotlinx.android.synthetic.main.view_game_field.view.*
import org.androidannotations.annotations.EViewGroup
import kotlin.math.roundToInt

@EViewGroup(R.layout.view_game_field)
open class GameFieldView : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var gameWidth: Int = 0
    private var gameHeight: Int = 0

    private var fieldWidth: Int = 0
    private var initialMeasure = true

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (initialMeasure) {
            val paddingPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.0f, resources.displayMetrics).roundToInt()

            fieldWidth = MeasureSpec.getSize(widthMeasureSpec) - 2 * paddingPx

            for (i in 0 until gameHeight) {
                gameFieldView.addView(createRowView().bind(gameWidth, i))
            }

            initialMeasure = false
        }
    }

    fun bind(gameWidth: Int, gameHeight: Int) {
        this.gameWidth = gameWidth
        this.gameHeight = gameHeight
    }

    private fun createRowView(): GameRowView {
        val rowView = GameRowView_.build(context)

        rowView.layoutParams = LayoutParams(fieldWidth, fieldWidth / gameWidth)

        return rowView
    }
}
