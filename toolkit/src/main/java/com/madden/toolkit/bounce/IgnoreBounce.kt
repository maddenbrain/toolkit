package com.madden.toolkit.bounce

/**
 * This implementation ignores all calls during bounceDelay after last call
 * Perfect for avoid button double tapping while animation or transition playing
 */
class IgnoreBounce(
    override val bounceDelay: Long
) : Bounce {
    private var lastCallTimeStamp: Long = 0

    override fun call(delay: Long, action: () -> Unit) {
        val timestamp = System.currentTimeMillis()
        if (timestamp - lastCallTimeStamp < delay) {
            return
        }
        action.invoke()
        lastCallTimeStamp = System.currentTimeMillis()
    }
}