package com.madden.toolkit.bounce

import android.os.Handler

/**
 * Last call will be executed after delay.
 * All calls except latest will be ignored.
 */

class LastBounce(
    override val bounceDelay: Long
) : Bounce {
    private val bounceHandler = Handler()
    private var lastRunnable: Runnable? = null

    override fun call(delay: Long, action: () -> Unit) {
        lastRunnable?.let {
            bounceHandler.removeCallbacks(it)
            lastRunnable = null
        }

        bounceHandler.postDelayed(Runnable {
            action.invoke()
            lastRunnable = null
        }.also { lastRunnable = it }, delay)
    }
}