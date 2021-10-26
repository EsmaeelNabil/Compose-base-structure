package com.esmaeel.composestructure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmaeel.composestructure.base.StateContainer

abstract class BaseComposeViewModel<STATE>(
    private val initialState: STATE,
    private val contextProviders: ContextProviders,
) : ViewModel() {


}