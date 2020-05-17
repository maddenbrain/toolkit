package com.madden.toolkit.bounce

/**
 * Default bounce implementation
 * Invokes action instantly
 */
class BounceImpl : Bounce {
    override val bounceDelay: Long = 0
    override fun call(delay: Long, action: () -> Unit) = action.invoke()
}