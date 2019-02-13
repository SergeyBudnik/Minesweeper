package com.bdev.minesweeper.ui.views.game

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.bdev.minesweeper.R
import com.bdev.minesweeper.data.Difficulty
import com.bdev.minesweeper.ui.activities.BaseActivity
import com.bdev.minesweeper.ui.activities.GameActivity
import com.bdev.minesweeper.ui.activities.GameActivity_
import com.bdev.minesweeper.ui.utils.RedirectUtils
import kotlinx.android.synthetic.main.view_game_won.view.*
import org.androidannotations.annotations.EViewGroup

@EViewGroup(R.layout.view_game_won)
open class GameWonView : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun init(difficulty: Difficulty) {
        startNewGameView.setOnClickListener {
            RedirectUtils.redirect(context as BaseActivity)
                    .to(GameActivity_::class.java)
                    .withAnim(R.anim.slide_open_enter_anim, R.anim.slide_open_exit_anim)
                    .withExtra(GameActivity.DIFFICULTY_EXTRA, difficulty)
                    .go(true)
        }

        goToMenuView.setOnClickListener {
            RedirectUtils.finish(context as BaseActivity, R.anim.slide_close_enter_anim, R.anim.slide_close_exit_anim)
        }
    }
}
