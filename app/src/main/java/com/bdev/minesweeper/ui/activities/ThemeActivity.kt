package com.bdev.minesweeper.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import com.bdev.minesweeper.R
import com.bdev.minesweeper.data.Theme
import com.bdev.minesweeper.ui.utils.RedirectUtils.Companion.finish
import kotlinx.android.synthetic.main.activity_theme.*
import kotlinx.android.synthetic.main.view_list_item_themes.view.*
import org.androidannotations.annotations.*

@EViewGroup(R.layout.view_list_item_themes)
open class ThemesListItemView(context: Context) : RelativeLayout(context) {
    fun bind(theme: Theme) {
        themeView.setImageDrawable(resources.getDrawable(theme.drawableId))

        themeTitleView.text = theme.title
        themeTitleView.setTextColor(resources.getColor(theme.titleColorId))
    }
}

@EBean
open class ThemesListAdapter : BaseAdapter() {
    @RootContext
    lateinit var context: Context

    override fun getView(index: Int, convertView: View?, parent: ViewGroup?): View {
        val view = if (convertView == null) {
            ThemesListItemView_.build(context)
        } else {
            convertView as ThemesListItemView
        }

        view.bind(getItem(index))

        return view
    }

    override fun getItem(index: Int): Theme {
        return Theme.values()[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return Theme.values().size
    }
}

@SuppressLint("Registered")
@EActivity(R.layout.activity_theme)
open class ThemeActivity : BaseActivity() {
    @Bean
    lateinit var themesListAdapter: ThemesListAdapter

    @AfterViews
    fun init() {
        themesListView.adapter = themesListAdapter

        backButtonView.setOnClickListener { goBack() }
    }

    override fun onBackPressed() {
        goBack()
    }

    private fun goBack() {
        finish(this, R.anim.slide_close_enter_anim, R.anim.slide_close_exit_anim)
    }
}
