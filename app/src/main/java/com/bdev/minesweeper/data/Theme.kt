package com.bdev.minesweeper.data

import com.bdev.minesweeper.R

enum class Theme(
        val title: String,
        val titleColorId: Int,
        val drawableId: Int
) {
    NIGHT("Night", R.color.white, R.drawable.theme_night),
    DAY("Day", R.color.black, R.drawable.theme_day)
}
