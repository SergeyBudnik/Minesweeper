package com.bdev.minesweeper.data

enum class Difficulty(
        val title: String,
        val width : Int,
        val height: Int,
        val mines: Int,
        val amountOfStars: Int
) {
    BEGINNER("Beginner", 9, 9, 10, 1),
    AMATEUR("Amateur", 12, 12, 23, 2),
    EXPERT("Expert", 12, 15, 37, 3)
}
