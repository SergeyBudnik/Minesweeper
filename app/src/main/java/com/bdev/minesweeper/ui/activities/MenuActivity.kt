package com.bdev.minesweeper.ui.activities

import android.annotation.SuppressLint
import com.bdev.minesweeper.R
import com.bdev.minesweeper.services.GameStateProviderService
import com.bdev.minesweeper.ui.utils.RedirectUtils.Companion.redirect
import kotlinx.android.synthetic.main.activity_menu.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EActivity

@SuppressLint("Registered")
@EActivity(R.layout.activity_menu)
open class MenuActivity : BaseActivity() {
    @Bean
    lateinit var gameStateProviderService: GameStateProviderService

    @AfterViews
    fun init() {
        newGameView.setOnClickListener { openNewGame() }

        if (gameStateProviderService.hasState()) {
            continueGameView.setButtonEnabled(true)
            continueGameView.setOnClickListener { openContinueGame() }
        } else {
            continueGameView.setButtonEnabled(false)
        }

        controlsView.setOnClickListener { openControls() }
        themeView.setOnClickListener { openThemes() }
        achievementsView.setOnClickListener { openAchievements() }
    }

    private fun openNewGame() {
        redirect(this)
                .to(GameDifficultyActivity_::class.java)
                .withAnim(R.anim.slide_open_enter_anim, R.anim.slide_open_exit_anim)
                .go(false)
    }

    private fun openContinueGame() {
        redirect(this)
                .to(GameActivity_::class.java)
                .withAnim(R.anim.slide_open_enter_anim, R.anim.slide_open_exit_anim)
                .withExtra( GameActivity.CONTINUE_EXTRA, true)
                .go(false)
    }

    private fun openControls() {
        redirect(this)
                .to(ControlsActivity_::class.java)
                .withAnim(R.anim.slide_open_enter_anim, R.anim.slide_open_exit_anim)
                .go(false)
    }

    private fun openThemes() {
        redirect(this)
                .to(ThemeActivity_::class.java)
                .withAnim(R.anim.slide_open_enter_anim, R.anim.slide_open_exit_anim)
                .go(false)
    }

    private fun openAchievements() {
        redirect(this)
                .to(AchievementsActivity_::class.java)
                .withAnim(R.anim.slide_open_enter_anim, R.anim.slide_open_exit_anim)
                .go(false)
    }
}
