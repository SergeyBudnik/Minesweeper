package com.bdev.minesweeper.ui.views.game

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import com.bdev.minesweeper.R
import com.bdev.minesweeper.services.GameFlagModeStateService
import com.bdev.minesweeper.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.view_game_flag_toggle.view.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.UiThread

@EViewGroup(R.layout.view_game_flag_toggle)
open class GameFlagToggleView : RelativeLayout {
    private inner class MyTouchListener : OnTouchListener {
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            return when {
                motionEvent.action == MotionEvent.ACTION_DOWN -> {
                    dragHandler.postDelayed({ startDrag() }, 100)
                    true
                }
                motionEvent.action == MotionEvent.ACTION_UP -> {
                    dragHandler.removeCallbacksAndMessages(null)

                    gameFlagModeStateService.setFlagMode(true)

                    return true
                }
                else -> false
            }
        }
    }

    @Bean
    lateinit var gameFlagModeStateService: GameFlagModeStateService

    private val dragHandler = Handler()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    @AfterViews
    fun bind() {
        gameFlagToggleView.setOnTouchListener(MyTouchListener())

        gameFlagModeStateService.subscribeFlagMode {
            (context as BaseActivity).runOnUiThread { setFlagMode(it) }
        }
    }

    @UiThread
    open fun startDrag() {
        val shadowBuilder = View.DragShadowBuilder(gameFlagView)

        gameFlagToggleView.startDrag(ClipData.newPlainText("", ""), shadowBuilder, gameFlagToggleView, 0)
    }

    private fun setFlagMode(toggled: Boolean) {
        gameFlagToggleView.alpha = if (toggled) {
            0.6f
        } else {
            1.0f
        }
    }
}
