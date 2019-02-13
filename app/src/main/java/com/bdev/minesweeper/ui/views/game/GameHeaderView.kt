package com.bdev.minesweeper.ui.views.game

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bdev.minesweeper.R
import com.bdev.minesweeper.services.GameStateProviderService
import com.bdev.minesweeper.ui.activities.GameActivity
import kotlinx.android.synthetic.main.view_game_header.view.*
import kotlinx.android.synthetic.main.view_game_header_timer.view.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EViewGroup
import org.androidannotations.annotations.UiThread
import java.util.*

@EViewGroup(R.layout.view_game_header_timer)
open class GameHeaderTimerView : LinearLayout {
    @Bean
    lateinit var gameStateProviderService: GameStateProviderService

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private lateinit var timeTimer: Timer

    private var lastUpdateTime = 0L

    fun onResume() {
        val gameState = gameStateProviderService.getState()

        lastUpdateTime = System.currentTimeMillis()

        timeTimer = Timer()

        timeTimer.schedule(object: TimerTask() {
            override fun run() {
                val currentTime = System.currentTimeMillis()

                gameState.millsPassed += (currentTime - lastUpdateTime)

                lastUpdateTime = currentTime

                setTimerText()
            }
        }, 0, 1000)
    }

    fun onPause() {
        timeTimer.cancel()
    }

    @UiThread
    open fun setTimerText() {
        val timePast = gameStateProviderService.getState().millsPassed

        val minutes = (timePast / 1000) / 60
        val seconds = (timePast / 1000) % 60

        timerView.text = String.format("%02d:%02d", minutes, seconds)
    }
}

@EViewGroup(R.layout.view_game_header)
open class GameHeaderView : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    @AfterViews
    fun init() {
        menuView.setOnClickListener {
            (context as GameActivity).goToMenu()
        }
    }

    fun bind() {
        // bombsView.text = "${gameFieldManipulator.getMinesLeft()}"
    }

    fun onResume() {
        timerContainerView.onResume()
    }

    fun onPause() {
        timerContainerView.onPause()
    }

    private fun onFlagsUpdated() {
        // bombsView.text = "${gameFieldManipulator.getMinesLeft()}"
    }
}
