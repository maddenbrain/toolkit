package com.madden.toolkit.assertions

class DebugAssertionHandler : AssertHandler {
    override fun onAssert(message: String) {
        throw IllegalStateException(message)
    }

    override fun onAssert(th: Throwable, message: String) {
        throw RuntimeException(message, th)
    }
}