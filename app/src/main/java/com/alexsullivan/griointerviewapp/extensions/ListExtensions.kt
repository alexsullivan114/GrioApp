package com.alexsullivan.griointerviewapp.extensions

/**
 * Simple helper method to do a structural equality check on two lists.
 * Kotlin exposes a similar method for arrays, but not for lists as far
 * as I can tell.
 */
infix fun <T> List<T>.contentEquals(other: List<T>): Boolean {
    // Contents of two lists aren't equal if we're not the same size!
    if (this.size != other.size) return false
    // Loop through and check each index with the index in the other list, and see if they're equal.
    return filterIndexed { index, value -> value != other[index] }.none()
}