package com.esmaeel.composestructure.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.esmaeel.composestructure.BaseComposeViewModel

//@Composable
//fun <STATE> StateComposable(
//    vm: BaseComposeViewModel<STATE>,
//    content: @Composable (screenState: ScreenState<STATE>) -> Unit,
//) {
//    val currentContext = LocalContext.current
//
//    val state = vm.stateContainer.state.collectAsState()
//    val isLoading = vm.stateContainer.isLoading.collectAsState()
//    val errorMessage = vm.stateContainer.errorMessage.collectAsState()
//    val hasError = vm.stateContainer.hasError()
//    content(
//        screenState = ScreenState(
//            state = state.value,
//            currentContext = currentContext,
//            isLoading = isLoading.value,
//            hasError = hasError,
//            errorMessage = errorMessage.value,
//        ),
//    )
//}
