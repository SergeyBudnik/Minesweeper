package com.bdev.minesweeper.services

import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import org.androidannotations.annotations.EBean

@EBean(scope = EBean.Scope.Singleton)
open class GameFlagModeStateService {
    private val flagModeObservable: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val disposables: MutableList<Disposable> = ArrayList()

    fun getFlagMode(): Boolean {
        return flagModeObservable.value ?: false
    }

    fun setFlagMode(enabled: Boolean) {
        flagModeObservable.onNext(enabled)
    }

    fun subscribeFlagMode(subscriber: (Boolean) -> Unit) {
        disposables.add(flagModeObservable.subscribe(subscriber))
    }

    fun unsubscribeAllFlagMode() {
        disposables.forEach { it.dispose() }
    }
}
