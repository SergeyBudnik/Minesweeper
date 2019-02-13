package com.bdev.minesweeper.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.bdev.minesweeper.R
import com.bdev.minesweeper.data.Difficulty
import com.bdev.minesweeper.ui.utils.RedirectUtils
import com.bdev.minesweeper.ui.utils.RedirectUtils.Companion.redirect
import kotlinx.android.synthetic.main.activity_game_difficulty.*
import kotlinx.android.synthetic.main.view_game_difficulty_item.view.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.EViewGroup

@EViewGroup(R.layout.view_game_difficulty_item)
open class GameDifficultyItemView : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun bind(difficulty: Difficulty) {
        difficultyTitleView.text = difficulty.title

        difficultyBombsAmount.text = "${difficulty.mines}"
        difficultyFieldSize.text = "${difficulty.width} x ${difficulty.height}"

        setStar(difficultyStar1View, difficulty.amountOfStars >= 1)
        setStar(difficultyStar2View, difficulty.amountOfStars >= 2)
        setStar(difficultyStar3View, difficulty.amountOfStars >= 3)
    }

    private fun setStar(starView: ImageView, condition: Boolean) {
        val activeColor = resources.getColor(R.color.item_default)
        val fadedColor = resources.getColor(R.color.background_dark)

        starView.setColorFilter(if (condition) {
            activeColor
        } else {
            fadedColor
        }, PorterDuff.Mode.SRC_IN)
    }
}

@SuppressLint("Registered")
@EActivity(R.layout.activity_game_difficulty)
open class GameDifficultyActivity : BaseActivity() {
    @AfterViews
    fun init() {
        init(beginnerDifficultyView, Difficulty.BEGINNER)
        init(amateurDifficultyView, Difficulty.AMATEUR)
        init(expertDifficultyView, Difficulty.EXPERT)

        backButtonView.setOnClickListener { goBack() }
    }

    private fun init(view: GameDifficultyItemView, difficulty: Difficulty) {
        view.bind(difficulty)
        view.setOnClickListener { startGame(difficulty) }
    }

    private fun startGame(difficulty: Difficulty) {
        redirect(this)
                .to(GameActivity_::class.java)
                .withAnim(R.anim.slide_open_enter_anim, R.anim.slide_open_exit_anim)
                .withExtra(GameActivity.DIFFICULTY_EXTRA, difficulty)
                .go(true)
    }

    override fun onBackPressed() {
        goBack()
    }

    private fun goBack() {
        RedirectUtils.finish(this, R.anim.slide_close_enter_anim, R.anim.slide_close_exit_anim)
    }
}
