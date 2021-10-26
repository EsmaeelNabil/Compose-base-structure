package com.esmaeel.composestructure.base

import android.content.Context

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesHandler @Inject constructor(@ApplicationContext val context: Context) {
    fun getString(stringId: Int) = context.getString(stringId)

    val UNKNOWN_ERROR = "getString(R.string.error)"
    val REAUTH_ERROR = "getString(R.string.reauth)"
    val NETWORK_ERROR = "getString(R.string.error_no_internet_connection)"
    val NETWORK_ERROR_TIMEOUT = "getString(R.string.timeout)"
}