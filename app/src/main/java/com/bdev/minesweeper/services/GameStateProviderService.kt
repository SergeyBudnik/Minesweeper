package com.bdev.minesweeper.services

import com.bdev.minesweeper.dao.GameStateDao
import com.bdev.minesweeper.data.GameState
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean

@EBean(scope = EBean.Scope.Singleton)
open class GameStateProviderService {
    @Bean
    lateinit var gameStateDao: GameStateDao

    fun initState(width: Int, height: Int, amountOfMines: Int) {
        gameStateDao.setGameState(GameState(width, height, amountOfMines))
    }

    fun hasState(): Boolean {
        return gameStateDao.getGameState() != null
    }

    fun getState(): GameState {
        return gameStateDao.getGameState() ?: throw RuntimeException()
    }

    fun setState(gameState: GameState) {
        gameStateDao.setGameState(gameState)
    }
}
