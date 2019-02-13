package com.bdev.minesweeper.ui.views.game

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.bdev.minesweeper.R
import kotlinx.android.synthetic.main.view_game_row.view.*
import org.androidannotations.annotations.EViewGroup

@EViewGroup(R.layout.view_game_row)
open class GameRowView : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var gameWidth: Int = 0
    private var y: Int = 0

    private var initialMeasure = true

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (initialMeasure) {
            initialMeasure = false

            val width = MeasureSpec.getSize(widthMeasureSpec)

            var leftWidth = width

            for (i in 0 until gameWidth) {
                val info = createCellView(leftWidth, i)

                leftWidth = info.second

                gameRowView.addView(info.first.bind(i, y))
            }
        }
    }

    fun bind(gameWidth: Int, y: Int): GameRowView {
        this.gameWidth = gameWidth
        this.y = y

        return this
    }

    private fun createCellView(width: Int, index: Int): Pair<GameCellView, Int> {
        val cellView = GameCellView_.build(context)

        val cellWidth = if (index == gameWidth - 1) {
            width
        } else {
            width / (gameWidth - index)
        }

        cellView.layoutParams = LayoutParams(cellWidth, cellWidth)

        return Pair(cellView, width - cellWidth)
    }
}
