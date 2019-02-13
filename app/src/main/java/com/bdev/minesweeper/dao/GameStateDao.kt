package com.bdev.minesweeper.dao

import android.content.Context
import com.bdev.minesweeper.data.GameState
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import org.codehaus.jackson.map.ObjectMapper

class GameStateModel {
    var gameState: GameState? = null
}

@EBean(scope = EBean.Scope.Singleton)
open class GameStateDao : CommonDao<GameStateModel>() {
    @RootContext
    lateinit var aContext: Context

    fun getGameState(): GameState? {
        readCache()

        return getValue().gameState
    }

    fun setGameState(gameState: GameState) {
        readCache()

        getValue().gameState = gameState

        persist()
    }

    override fun deserialize(json: String): GameStateModel {
        return ObjectMapper().readValue(json, GameStateModel::class.java)
    }

    override fun getContext(): Context {
        return aContext
    }

    override fun getFileName(): String {
        return "game-state.data"
    }

    override fun newInstance(): GameStateModel {
        return GameStateModel()
    }
}
