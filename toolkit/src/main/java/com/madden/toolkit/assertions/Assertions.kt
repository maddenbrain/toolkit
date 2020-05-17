@file:Suppress("unused")

package com.madden.toolkit.assertions

import android.os.Looper
import java.util.*

private const val ILLEGAL_CONDITION = "Illegal condition!\n"
private const val EXPECTED_MESSAGE = "Expected %s, received %s.\n"
private const val ILLEGAL_THREAD = "IllegalThread %s! Shoud be %s.\n"
private const val SHOULD_BE_NULL = "Variable should be null!\n"
private const val SHOULD_BE_NON_NULL = "Potential null pointer!\n"
private const val AT_LEAST = "Value %s should be at least %s\n"
private const val NOT_MORE_THAN = "Value %s should be not more than %s\n"

val assertionHandlers: MutableList<AssertHandler> = LinkedList<AssertHandler>()

fun fail(message: String) {
    for (handler in assertionHandlers)
        handler.onAssert(message)
}

fun fail(th: Throwable, message: String) {
    for (handler in assertionHandlers)
        handler.onAssert(th, message)
}

fun assertNonNull(obj: Any?): Boolean = assertNonNull(obj, SHOULD_BE_NON_NULL)

fun assertNonNull(obj: Any?, message: String): Boolean {
    val result = obj == null
    if (result) {
        fail(message)
    }
    return result
}

fun assertNull(obj: Any?): Boolean = assertNull(obj, SHOULD_BE_NULL)

fun assertNull(obj: Any?, message: String): Boolean {
    val result = obj != null
    if (result) {
        fail(message)
    }
    return result
}

fun assertFalse(shouldBeFalse: Boolean): Boolean {
    return assertTrue(!shouldBeFalse, ILLEGAL_CONDITION)
}

fun assertFalse(shouldBeFalse: Boolean, message: String): Boolean {
    return assertTrue(!shouldBeFalse, message)
}

fun assertTrue(shouldBeTrue: Boolean): Boolean {
    return assertTrue(shouldBeTrue, ILLEGAL_CONDITION)
}

fun assertTrue(shouldBeTrue: Boolean, message: String): Boolean {
    if (!shouldBeTrue) {
        fail(message)
    }
    return !shouldBeTrue
}

fun <T> assertInstanceOf(target: Any, type: Class<T>): Boolean {
    return assertInstanceOf(
        target,
        type,
        String.format(EXPECTED_MESSAGE, type.simpleName, target.javaClass.simpleName)
    )
}

fun <T> assertInstanceOf(target: Any, type: Class<T>, message: String): Boolean {
    val result = !type.isAssignableFrom(target.javaClass)
    if (result) {
        fail(message)
    }
    return result
}

fun assertEquals(target: Any, shouldBe: Any): Boolean {
    return assertEquals(
        target,
        shouldBe,
        String.format(EXPECTED_MESSAGE, shouldBe.toString(), target.toString())
    )
}

fun assertEquals(target: Any, shouldBe: Any, message: String): Boolean {
    val result = target != shouldBe
    if (result) {
        fail(message)
    }
    return result
}

fun assertEqualInts(target: Int, shouldBe: Int): Boolean {
    return assertEqualInts(
        target,
        shouldBe,
        String.format(EXPECTED_MESSAGE, shouldBe, target)
    )
}

fun assertEqualInts(target: Int, shouldBe: Int, message: String): Boolean {
    val result = target != shouldBe
    if (result) {
        fail(message)
    }
    return result
}

fun assertBackgroundThread(): Boolean {
    val isRightThread = Thread.currentThread() !== Looper.getMainLooper().thread
    if (!isRightThread) {
        fail(String.format(ILLEGAL_THREAD, Thread.currentThread(), "any non-ui thread"))
    }
    return !isRightThread
}

fun assertMainThread(): Boolean = assertThread(Looper.getMainLooper().thread)

fun assertThread(currentShouldBe: Thread): Boolean {
    val isRightThread = Thread.currentThread() === currentShouldBe
    if (!isRightThread) {
        fail(String.format(ILLEGAL_THREAD, Thread.currentThread(), currentShouldBe))
    }
    return !isRightThread
}

fun assertAtLeast(target: Int, shouldBe: Int): Boolean {
    return assertAtLeast(
        target,
        shouldBe,
        String.format(AT_LEAST, target, shouldBe)
    )
}

fun assertAtLeast(target: Int, shouldBe: Int, message: String): Boolean {
    val result = target < shouldBe
    if (result) {
        fail(message)
    }
    return result
}

fun assertNotMoreThan(target: Int, shouldBe: Int): Boolean {
    return assertNotMoreThan(
        target,
        shouldBe,
        String.format(NOT_MORE_THAN, target, shouldBe)
    )
}

fun assertNotMoreThan(target: Int, shouldBe: Int, message: String): Boolean {
    val result = target > shouldBe
    if (result) {
        fail(message)
    }
    return result
}