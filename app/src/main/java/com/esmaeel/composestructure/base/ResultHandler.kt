package com.esmaeel.composestructure.base

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResultHandler @Inject constructor(private val errorHandler: ErrorHandler) {
    suspend fun <R> resultHandler(func: suspend () -> R): ResultOf<R> {
        return try {
            ResultOf.Success(func())
        } catch (throwable: Throwable) {
            errorHandler.getErrorFromResult(throwable)
        }
    }
}