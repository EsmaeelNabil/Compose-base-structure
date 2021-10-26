package com.esmaeel.composestructure.base

import com.esmaeel.composestructure.data.ErrorModel
import com.google.gson.Gson
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandler @Inject constructor(
    private val resourcesHandler: ResourcesHandler,
) {
    fun <R> getErrorFromResult(throwable: Throwable): ResultOf<R> {
        return when (throwable) {
            // network timeout exception due to OkHttpClient timeout configurations
            is TimeoutCancellationException -> ResultOf.Error(resourcesHandler.NETWORK_ERROR_TIMEOUT)

            // might be due to no wifi enabled or network.
            is IOException -> ResultOf.Error(resourcesHandler.NETWORK_ERROR)

            // Server has responded and now we need to check for the status Code (404,401, .... )
            is HttpException -> ResultOf.Error(getNetworkExceptionMessage(throwable))

            // some other exception
            else -> ResultOf.Error(throwable.message ?: resourcesHandler.UNKNOWN_ERROR)
        }
    }

    private fun getNetworkExceptionMessage(throwable: HttpException): String {
        return when (throwable.code()) {
            401 -> throw AuthException(resourcesHandler.REAUTH_ERROR)
            else -> getErrorFrom(throwable)
        }
    }


    fun getErrorFrom(throwable: HttpException): String {
        return try {
            val model = Gson().fromJson(
                throwable.response()?.errorBody()?.string(),
                ErrorModel::class.java
            )

            val errorMessage = if (model.error.message.isNullOrEmpty().not()) {
                model.error.message
            } else {
                model.error.fields?.type
            }

            errorMessage ?: resourcesHandler.UNKNOWN_ERROR

        } catch (exception: Exception) {
            // TODO: 7/28/21 get unhandled error models
            Timber.e(throwable.toString())
            resourcesHandler.UNKNOWN_ERROR
        }
    }
}



