package com.bdev.minesweeper.services

import com.bdev.minesweeper.data.GameCellState
import com.bdev.minesweeper.data.GameState
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean
import java.util.*

@EBean
open class GameStateManipulatorService {
    @Bean
    lateinit var gameStateProviderService: GameStateProviderService
    @Bean
    lateinit var gameFlagModeStateService: GameFlagModeStateService
    @Bean
    lateinit var gameStateObserverService: GameStateObserverService

    fun modifyCell(x: Int, y: Int) {
        val gameState = gameStateProviderService.getState()

        val toggled = gameFlagModeStateService.getFlagMode()

        if (gameState.cells[x][y] == GameCellState.MARKED) {
            gameState.cells[x][y] = GameCellState.CLOSED
        } else {
            if (toggled) {
                gameFlagModeStateService.setFlagMode(false)

                setCellMarked(x, y)
            } else {
                setCellOpened(gameState, x, y)
            }
        }
    }

    private fun setCellOpened(gameState: GameState, x: Int, y: Int) {
        if (!gameState.firstCellOpened) {
            initMines(gameState, x, y)
        }

        doSetCellOpened(gameState, x, y)
    }

    private fun setCellMarked(x: Int, y: Int) {
        gameStateObserverService.notifyCellUpdate(x, y, GameCellState.MARKED, false, -1)
    }

    private fun initMines(gameState: GameState, safeX: Int, safeY: Int) {
        gameState.firstCellOpened = true

        val random = Random()

        for (i in 0 until gameState.amountOfMines) {
            while (true) {
                val x = random.nextInt(gameState.width)
                val y = random.nextInt(gameState.height)

                val safe = Math.abs(safeX - x) + Math.abs(safeY - y) <= 2

                if (!gameState.mines[x][y] && !safe) {
                    gameState.mines[x][y] = true
                    break
                }
            }
        }
    }

    private fun doSetCellOpened(gameState: GameState, x: Int, y: Int) {
        gameState.cells[x][y] = GameCellState.OPENED
        gameState.openedAmount++

        if (gameState.mines[x][y]) {
            gameStateObserverService.notifyCellUpdate(x, y, GameCellState.OPENED, gameState.mines[x][y], -1)
            gameStateObserverService.notifyGameLost()
        } else {
            val minesAround = gameState.getMinesAround(x, y)

            gameStateObserverService.notifyCellUpdate(x, y, GameCellState.OPENED, gameState.mines[x][y], minesAround)

            if (minesAround == 0) {
                gameState.overNeighborsIfState(
                        x, y,
                        { it == GameCellState.CLOSED }
                ) { i, j -> doSetCellOpened(gameState, i, j) }
            }
        }

        if (gameState.openedAmount == gameState.width * gameState.height - gameState.amountOfMines) {
            gameStateObserverService.notifyGameWon()
        }
    }
}
