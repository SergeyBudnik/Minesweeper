package com.bdev.minesweeper.data

enum class GameCellState {
    OPENED, MARKED, CLOSED
}

class GameState(
        val width: Int,
        val height: Int,
        val amountOfMines: Int
) {
    val mines = Array(width) { _ -> Array(height) { false } }
    val cells = Array(width) { _ -> Array(height) { GameCellState.CLOSED } }

    var firstCellOpened = false
    var openedAmount = 0
    var millsPassed = 0L

    fun getMinesAround(x: Int, y: Int): Int {
        var n = 0

        overNeighborsIfState(
                x, y, { true }
        ) { i, j -> if (mines[i][j]) { n++ } }

        return n
    }

    fun overNeighborsIfState(
            x: Int, y: Int,
            condition: (GameCellState) -> Boolean,
            action: (Int, Int) -> Unit
    ) {
        for (i in (x - 1)..(x + 1)) {
            for (j in (y - 1)..(y + 1)) {
                val xInRange = i in 0..(width - 1)
                val yInRange = j in 0..(height - 1)
                val notSame = i != x || j != y

                if (xInRange && yInRange && notSame) {
                    if (condition.invoke(cells[i][j])) {
                        action.invoke(i, j)
                    }
                }
            }
        }
    }
}
