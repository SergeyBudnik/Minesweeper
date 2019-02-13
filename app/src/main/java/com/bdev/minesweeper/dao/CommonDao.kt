package com.bdev.minesweeper.dao

import android.content.Context
import android.util.Log
import com.bdev.minesweeper.utils.StorageUtils
import org.codehaus.jackson.map.ObjectMapper

import java.io.IOException
import java.util.concurrent.atomic.AtomicReference

abstract class CommonDao<out T> {
    private val cache = AtomicReference<T>(null)

    protected abstract fun getContext(): Context
    protected abstract fun getFileName(): String
    protected abstract fun newInstance(): T
    protected abstract fun deserialize(json: String): T

    protected fun getValue(): T {
        return cache.get()
    }

    protected fun persist() {
        if (cache.get() != null) {
            try {
                StorageUtils.writeData(
                        getContext(),
                        getFileName(),
                        ObjectMapper().writeValueAsString(cache.get())
                )
            } catch (e: IOException) {
                Log.e("CommonDao", "Persist failed", e)
            }
        }
    }

    protected fun readCache() {
        if (cache.get() == null) {
            try {
                val json = StorageUtils.readData(getContext(), getFileName())

                if (json != null) {
                    cache.set(deserialize(json))
                } else {
                    cache.set(null)
                }
            } catch (e: IOException) {
                Log.e("CommonDao", "Reading failed", e)
            }
        }

        if (cache.get() == null) {
            cache.set(newInstance())
        }
    }

    protected fun inTransaction(action: () -> Unit) {
        readCache()

        action.invoke()

        persist()
    }
}