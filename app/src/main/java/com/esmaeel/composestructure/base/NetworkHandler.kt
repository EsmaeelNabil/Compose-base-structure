package com.esmaeel.composestructure.base

import com.esmaeel.composestructure.ContextProviders
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NetworkHandler @Inject constructor(
    private val contextProviders: ContextProviders,
    private val resourcesHandler: ResourcesHandler,
    private val errorHandler: ErrorHandler,
) {
    fun <T> networkHandler(fetch: suspend () -> T) = flow {

        // trying to invoke the passed function
        // and emit it's value
        try {
            emit(fetch.invoke())
        }

        // there has been an exception
        // so we might need to respond to it differently
        catch (throwable: Throwable) {

            when (throwable) {

                // network timeout exception due to OkHttpClient timeout configurations
                is TimeoutCancellationException -> throw AppException(resourcesHandler.NETWORK_ERROR_TIMEOUT)

                // might be due to no wifi enabled or network.
                is IOException -> throw AppException(resourcesHandler.NETWORK_ERROR)

                // Server has responded and now we need to check for the status Code (404,401, .... )
                is HttpException -> handleNetworkException(throwable)

                // some other exception
                else -> throw AppException(throwable.message)
            }

        }

    }.flowOn(contextProviders.IO)


    private fun handleNetworkException(throwable: HttpException) {
        when (throwable.code()) {
            401 -> throw AuthException(resourcesHandler.REAUTH_ERROR)
            else -> throw AppException(errorHandler.getErrorFrom(throwable))
        }
    }
}