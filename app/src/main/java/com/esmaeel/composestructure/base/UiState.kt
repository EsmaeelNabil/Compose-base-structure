package com.esmaeel.composestructure.base

/**
 * Immutable data class that allows for loading, data, and exception to be managed independently.
 *
 * This is useful for screens that want to show the last successful result while loading or a later
 * refresh has caused an error.
 */
data class UiState<T>(
    val loading: Boolean = false,
    val error: String? = null,
    val data: T? = null
) {
    /**
     * True if this contains an error
     */
    val hasError: Boolean
        get() = error != null

    /**
     * True if this represents a first load
     */
    val initialLoad: Boolean
        get() = data == null && loading && !hasError
}

/**
 * Copy a UiState<T> based on a Result<T>.
 *
 * Result.Success will set all fields
 * Result.Error will reset loading and exception only
 */
fun <T> UiState<T>.copyWithResult(value: ResultOf<T>): UiState<T> {
    return when (value) {
        is ResultOf.Success -> copy(loading = false, error = null, data = value.data)
        is ResultOf.Error -> copy(loading = false, error = value.error)
    }
}