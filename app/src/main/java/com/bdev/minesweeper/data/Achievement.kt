package com.bdev.minesweeper.data

import com.bdev.minesweeper.R

enum class Achievement(
        val iconId: Int,
        val iconColorId: Int,
        val text: String
) {
    PLAY_FIRST_GAME(R.drawable.ic_medal, R.color.gold, "Win your first game"),
    WIN_10_GAMES(R.drawable.ic_achievement, R.color.bronze, "Win 10 games"),
    WIN_100_GAMES(R.drawable.ic_achievement, R.color.silver, "Win 100 games"),
    WIN_1000_GAMES(R.drawable.ic_achievement, R.color.gold, "Win 1000 games")
}
