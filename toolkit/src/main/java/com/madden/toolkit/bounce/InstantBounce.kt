package com.madden.toolkit.bounce

import android.os.Handler
import kotlin.math.min

/**
 * Similar to ignore & last bounces
 * If delay passed - first call will be executed instantly
 * In case of call spam - only latest call well be executed after delay
 * Warn: Works asynchronously!
 */
class InstantBounce(
    override val bounceDelay: Long
) : Bounce {
    private val bounceHandler = Handler()
    private var lastRunnable: Runnable? = null
    private var lastCallTimeStamp: Long = 0

    override fun call(delay: Long, action: () -> Unit) {
        val timestamp = System.currentTimeMillis()

        lastRunnable?.let {
            bounceHandler.removeCallbacks(it)
            lastRunnable = null
        }

        var nextCallDelay = delay - (timestamp - lastCallTimeStamp)
        nextCallDelay = min(nextCallDelay, 0)

        bounceHandler.postDelayed(Runnable {
            action.invoke()
            lastCallTimeStamp = System.currentTimeMillis()
            lastRunnable = null
        }.also { lastRunnable = it }, nextCallDelay)
    }
}