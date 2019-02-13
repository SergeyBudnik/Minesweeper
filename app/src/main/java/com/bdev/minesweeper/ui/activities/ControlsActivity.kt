package com.bdev.minesweeper.ui.activities

import android.annotation.SuppressLint
import com.bdev.minesweeper.R
import com.bdev.minesweeper.ui.utils.RedirectUtils.Companion.finish
import kotlinx.android.synthetic.main.activity_theme.*
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity

@SuppressLint("Registered")
@EActivity(R.layout.activity_controls)
open class ControlsActivity : BaseActivity() {
    @AfterViews
    fun init() {
        backButtonView.setOnClickListener { goBack() }
    }

    override fun onBackPressed() {
        goBack()
    }

    private fun goBack() {
        finish(this, R.anim.slide_close_enter_anim, R.anim.slide_close_exit_anim)
    }
}
