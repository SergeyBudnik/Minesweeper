package com.bdev.minesweeper.services

import android.content.Context
import com.bdev.minesweeper.data.GameCellState
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean

class GameCellStateUpdateInfo(
        val x: Int,
        val y: Int,
        val state: GameCellState,
        val hasMine: Boolean,
        val minesAround: Int
)

@EBean(scope = EBean.Scope.Singleton)
open class GameStateObserverService {
    @Bean
    lateinit var gameStateProviderService: GameStateProviderService

    private lateinit var cellUpdatedObservable: BehaviorSubject<GameCellStateUpdateInfo>

    private lateinit var gameLostObservable: BehaviorSubject<Boolean>
    private lateinit var gameWonObservable: BehaviorSubject<Boolean>

    private val disposables: MutableMap<Int, MutableList<Disposable>> = HashMap()

    fun init() {
        cellUpdatedObservable = BehaviorSubject.create()

        gameLostObservable = BehaviorSubject.create()
        gameWonObservable = BehaviorSubject.create()
    }

    fun notifyCellUpdate(x: Int, y: Int, cellState: GameCellState, hasMine: Boolean, minesAround: Int) {
        cellUpdatedObservable.onNext(GameCellStateUpdateInfo(x, y, cellState, hasMine, minesAround))

        //gameStateProviderService.setState()
    }

    fun subscribeOnCellUpdate(context: Context, subscriber: (GameCellStateUpdateInfo) -> Unit) {
        addToContextDisposables(context, cellUpdatedObservable.subscribe(subscriber))
    }

    fun notifyGameWon() {
        gameWonObservable.onNext(true)
    }

    fun subscribeOnGameWon(context: Context, subscriber: (Boolean) -> Unit) {
        addToContextDisposables(context, gameWonObservable.subscribe(subscriber))
    }

    fun notifyGameLost() {
        gameLostObservable.onNext(true)
    }

    fun subscribeOnGameLost(context: Context, subscriber: (Boolean) -> Unit) {
        addToContextDisposables(context, gameLostObservable.subscribe(subscriber))
    }

    fun unsubscribeAll(context: Context) {
        (disposables[context.hashCode()] ?: emptyList<Disposable>()).forEach { it.dispose() }
    }

    private fun addToContextDisposables(context: Context, disposable: Disposable) {
        val contextDisposables = disposables[context.hashCode()] ?: ArrayList()

        contextDisposables.add(disposable)

        disposables[context.hashCode()] = contextDisposables
    }
}
