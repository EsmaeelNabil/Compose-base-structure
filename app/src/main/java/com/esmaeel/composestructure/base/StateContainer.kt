package com.esmaeel.composestructure.base

import com.esmaeel.composestructure.ContextProviders
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StateContainer<STATE>(
    private val initialState: STATE,
    private val contextProviders: ContextProviders,
    private val viewModelScope: CoroutineScope,
) {

    val state = MutableStateFlow(initialState)
    val isLoading = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")
    val authRequired = MutableStateFlow(false)
    fun hasError() = errorMessage.value.isNotEmpty() && !authRequired.value
    fun resetState() = initialState.also { state.value = it }

    /**
     *  used for two scenarios :
     *  1 - set the screen Data state and reset ( loading , error , reAuth ) on success
     *  2 - change  ( loading , error , reAuth ) state
     */
    fun setState(
        loading: Boolean = false,
        error: String = "",
        reAuth: Boolean = false,
        newState: (STATE.() -> STATE)? = null,
    ) {
        isLoading.value = loading
        errorMessage.value = error
        authRequired.value = reAuth

        newState?.let {
            state.value = newState(state.value)
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is AuthException -> setState(reAuth = true, error = "Auth Required Login Again!")
            else -> setState(error = throwable.message.toString())
        }
    }

    fun launchBlock(emitLoading: Boolean = true, block: suspend CoroutineScope.() -> Unit) {
        setState(loading = emitLoading)
        viewModelScope.launch(contextProviders.Main + coroutineExceptionHandler) {
            block.invoke(this)
        }
    }

}