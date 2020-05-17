package com.madden.toolkit.bounce

import android.os.Handler

/**
 * Bounce with guarantee to invoke calls every bounceDelay period, if call not null.
 */
class TimerBounce(
    override val bounceDelay: Long
) : Bounce {
    private val bounceHandler = Handler()
    private var lastRunnable: Runnable? = null
    private var lastCallTimeStamp: Long = 0

    override fun call(delay: Long, action: () -> Unit) {
        val ts = System.currentTimeMillis()
        if (lastCallTimeStamp == 0L) {
            lastCallTimeStamp = ts
        }
        val diff = Math.min(ts - lastCallTimeStamp, delay)

        lastRunnable?.let {
            bounceHandler.removeCallbacks(it)
            lastRunnable = null
        }

        bounceHandler.postDelayed(Runnable {
            lastCallTimeStamp = 0
            action.invoke()
            lastRunnable = null
        }.also { lastRunnable = it }, delay - diff)
    }
}