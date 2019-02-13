package com.bdev.minesweeper.ui.utils

import android.content.Intent
import com.bdev.minesweeper.ui.activities.BaseActivity
import java.io.Serializable

class RedirectUtils private constructor(private val activity: BaseActivity) {
    private lateinit var target: Class<out BaseActivity>

    private var extras: MutableMap<String, Serializable> = HashMap()

    private var enterAnim: Int = 0
    private var exitAnim: Int = 0

    companion object {
        fun redirect(current: BaseActivity): RedirectUtils {
            return RedirectUtils(current)
        }

        fun finish(current: BaseActivity, enterAnim: Int, exitAnim: Int) {
            current.finish()

            current.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    fun to(target: Class<out BaseActivity>): RedirectUtils {
        this.target = target

        return this
    }

    fun withExtra(key: String, value: Serializable): RedirectUtils {
        this.extras[key] = value

        return this
    }

    fun withAnim(enterAnim: Int, exitAnim: Int): RedirectUtils {
        this.enterAnim = enterAnim
        this.exitAnim = exitAnim

        return this
    }

    fun go(finish: Boolean) {
        val intent = Intent(activity, target)

        for (extraKey in extras.keys) {
            intent.putExtra(extraKey, extras[extraKey])
        }

        activity.startActivity(intent)
        activity.overridePendingTransition(enterAnim, exitAnim)

        if (finish) {
            activity.finish()
        }
    }

    fun goForResult(code: Int) {
        val i = Intent(activity, target)

        for (extraKey in extras.keys) {
            i.putExtra(extraKey, extras[extraKey])
        }

        activity.startActivityForResult(i, code)
        activity.overridePendingTransition(enterAnim, exitAnim)
    }
}
