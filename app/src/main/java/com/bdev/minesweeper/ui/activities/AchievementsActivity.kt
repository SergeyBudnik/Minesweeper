package com.bdev.minesweeper.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import com.bdev.minesweeper.R
import com.bdev.minesweeper.data.Achievement
import com.bdev.minesweeper.ui.utils.RedirectUtils.Companion.finish
import kotlinx.android.synthetic.main.activity_achievements.*
import kotlinx.android.synthetic.main.view_list_item_achievements.view.*
import org.androidannotations.annotations.*

@EViewGroup(R.layout.view_list_item_achievements)
open class AchievementsListItemView(context: Context) : RelativeLayout(context) {
    fun bind(achievement: Achievement) {
        achievementIconView.setImageDrawable(resources.getDrawable(achievement.iconId))
        achievementIconView.setColorFilter(resources.getColor(achievement.iconColorId), PorterDuff.Mode.SRC_IN)
        achievementNameView.text = achievement.text
    }
}

@EBean
open class AchievementsListAdapter : BaseAdapter() {
    @RootContext
    lateinit var context: Context

    override fun getView(index: Int, convertView: View?, parent: ViewGroup?): View {
        val view = if (convertView == null) {
            AchievementsListItemView_.build(context)
        } else {
            convertView as AchievementsListItemView
        }

        view.bind(getItem(index))

        return view
    }

    override fun getItem(index: Int): Achievement {
        return Achievement.values()[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return Achievement.values().size
    }
}

@SuppressLint("Registered")
@EActivity(R.layout.activity_achievements)
open class AchievementsActivity : BaseActivity() {
    @Bean
    lateinit var achievementsListAdapter: AchievementsListAdapter

    @AfterViews
    fun init() {
        achievementsListView.adapter = achievementsListAdapter

        backButtonView.setOnClickListener { goBack() }
    }

    override fun onBackPressed() {
        goBack()
    }

    private fun goBack() {
        finish(this, R.anim.slide_close_enter_anim, R.anim.slide_close_exit_anim)
    }
}
