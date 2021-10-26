package com.esmaeel.composestructure.base

/**
 * A generic class that holds a value or an exception
 */
sealed class ResultOf<out R> {

    data class Success<out T>(val data: T) : ResultOf<T>()
    data class Error(val error: String) : ResultOf<Nothing>()
}

/**
 * `true` if [ResultOf] is of type [Success] & holds non-null [Success.data].
 */
val ResultOf<*>.succeeded
    get() = this is ResultOf.Success && data != null

fun <T> ResultOf<T>.successOr(fallback: T): T {
    return (this as? ResultOf.Success<T>)?.data ?: fallback
}