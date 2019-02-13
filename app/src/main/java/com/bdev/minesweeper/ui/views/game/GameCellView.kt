package com.bdev.minesweeper.ui.views.game

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View.OnDragListener
import com.bdev.minesweeper.R
import com.bdev.minesweeper.data.GameCellState
import com.bdev.minesweeper.data.GameCellState.MARKED
import com.bdev.minesweeper.data.GameCellState.OPENED
import com.bdev.minesweeper.services.GameStateManipulatorService
import com.bdev.minesweeper.services.GameStateObserverService
import com.bdev.minesweeper.services.GameStateProviderService
import com.bdev.minesweeper.ui.activities.BaseActivity
import com.bdev.minesweeper.ui.views.common.SquareRelativeLayout
import kotlinx.android.synthetic.main.view_game_cell.view.*
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup

@EViewGroup(R.layout.view_game_cell)
open class GameCellView : SquareRelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    @Bean
    lateinit var gameStateManipulatorService: GameStateManipulatorService
    @Bean
    lateinit var gameStateObserverService: GameStateObserverService
    @Bean
    lateinit var gameStateProviderService: GameStateProviderService

    private var x: Int = -1
    private var y: Int = -1

    fun bind(x: Int, y: Int): GameCellView {
        this.x = x
        this.y = y

        gameCellView.setOnClickListener {
            gameStateManipulatorService.modifyCell(x, y)
        }

        val gameState = gameStateProviderService.getState()

        setStyle(
                gameState.cells[x][y],
                gameState.mines[x][y],
                gameState.getMinesAround(x, y)
        )

        gameStateObserverService.subscribeOnCellUpdate(context) { it ->
            if (it.x == x && it.y == y) {
                (context as BaseActivity).runOnUiThread { setStyle(it.state, it.hasMine, it.minesAround) }
            }
        }

        gameCellView.setOnDragListener(OnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> return@OnDragListener true

                DragEvent.ACTION_DRAG_ENTERED -> { v.setBackgroundColor(Color.LTGRAY)
                    return@OnDragListener true
                }

                DragEvent.ACTION_DRAG_LOCATION -> true

                DragEvent.ACTION_DRAG_EXITED -> {
                    v.setBackgroundColor(Color.TRANSPARENT)
                    return@OnDragListener true
                }

                DragEvent.ACTION_DROP -> {
                    v.setBackgroundColor(Color.TRANSPARENT)

                    // setFlag() ToDo

                    return@OnDragListener true
                }

                DragEvent.ACTION_DRAG_ENDED -> return@OnDragListener true

                else -> return@OnDragListener false
            }
        })

        return this
    }

    private fun setStyle(state: GameCellState, hasMine: Boolean, minesAround: Int) {
        openedCellView.visibility = if (state == OPENED) { VISIBLE } else { GONE }
        closedCellView.visibility = if (state == OPENED) { GONE } else { VISIBLE }

        flagView.visibility = if (state == MARKED) { VISIBLE } else { GONE }

        if (hasMine) {
            bombView.visibility = VISIBLE
        } else {
            bombView.visibility = GONE

            if (minesAround != 0) {
                minesAroundView.visibility = VISIBLE
                minesAroundView.text = "$minesAround"
            } else {
                minesAroundView.visibility = GONE
            }
        }
    }
}
