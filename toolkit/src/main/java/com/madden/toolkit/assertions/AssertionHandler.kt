package com.madden.toolkit.assertions

interface AssertHandler {
    fun onAssert(message: String)
    fun onAssert(th: Throwable, message: String)
}