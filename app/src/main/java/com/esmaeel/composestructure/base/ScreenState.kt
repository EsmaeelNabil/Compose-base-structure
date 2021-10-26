package com.esmaeel.composestructure.base

import android.content.Context

data class ScreenState<STATE>(
    val state: STATE,
    val currentContext: Context,
    val isLoading: Boolean,
    val hasError: Boolean,
    val errorMessage: String,
)