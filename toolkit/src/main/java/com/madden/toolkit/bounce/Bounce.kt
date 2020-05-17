package com.madden.toolkit.bounce

interface Bounce {
    val bounceDelay: Long
    fun call(delay: Long = bounceDelay, action: () -> Unit)
}