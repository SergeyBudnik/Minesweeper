package com.bdev.minesweeper.ui.activities

import android.annotation.SuppressLint
import android.view.View
import com.bdev.minesweeper.R
import com.bdev.minesweeper.data.Difficulty
import com.bdev.minesweeper.services.GameFlagModeStateService
import com.bdev.minesweeper.services.GameStateObserverService
import com.bdev.minesweeper.services.GameStateProviderService
import com.bdev.minesweeper.ui.utils.RedirectUtils.Companion.finish
import kotlinx.android.synthetic.main.activity_game.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.Extra

@SuppressLint("Registered")
@EActivity(R.layout.activity_game)
open class GameActivity : BaseActivity() {
    companion object {
        const val DIFFICULTY_EXTRA: String = "DIFFICULTY_EXTRA"
        const val CONTINUE_EXTRA: String = "CONTINUE_EXTRA"
    }

    @Extra(DIFFICULTY_EXTRA)
    @JvmField
    var difficulty: Difficulty = Difficulty.BEGINNER

    @Extra(CONTINUE_EXTRA)
    @JvmField
    var doContinue: Boolean = false

    @Bean
    lateinit var gameStateProviderService: GameStateProviderService
    @Bean
    lateinit var gameStateObserverService: GameStateObserverService
    @Bean
    lateinit var gameFlagModeStateService: GameFlagModeStateService

    @AfterViews
    fun init() {
        if (!doContinue) {
            gameStateProviderService.initState(difficulty.width, difficulty.height, difficulty.mines)
        }

        val state = gameStateProviderService.getState()

        gameFieldContainerView.bind(state.width, state.height)

        gameWonView.init(difficulty)
        gameLostView.init(difficulty)
    }

    override fun onStart() {
        super.onStart()

        gameStateObserverService.init()

        gameStateObserverService.subscribeOnGameLost(this) {
            gameLostView.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()

        headerView.onPause()
    }

    override fun onResume() {
        super.onResume()

        headerView.onResume()
    }

    override fun onStop() {
        super.onStop()

        gameStateObserverService.unsubscribeAll(this)
        gameFlagModeStateService.unsubscribeAllFlagMode()
    }

    fun goToMenu() {
        //gameProgressService.saveGameField(game.getGameField())

        finish(this, R.anim.slide_close_enter_anim, R.anim.slide_close_exit_anim)
    }
}
