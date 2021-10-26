package com.esmaeel.composestructure.base.paging

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

val LoadState.error: LoadState.Error?
    get() {
        return if (this is LoadState.Error)
            this
        else null
    }


@Composable
fun <T : Any> PagingState(
    lazyPagingItems: LazyPagingItems<T>,
    onInitialLoading: @Composable () -> Unit,
    onInitialError: @Composable (LoadState.Error?) -> Unit,
    onAppendLoading: @Composable () -> Unit,
    onAppendError: @Composable (LoadState.Error?) -> Unit,
    onNoData: @Composable () -> Unit,
) {

    lazyPagingItems.loadState.apply {
        when {
            refresh is LoadState.Loading -> onInitialLoading()
            refresh is LoadState.Error -> onInitialError(refresh.error)
            append is LoadState.Loading -> onAppendLoading()
            append is LoadState.Error -> onAppendError(append.error)
            refresh is LoadState.NotLoading -> {
                if (!(refresh as LoadState.NotLoading).endOfPaginationReached && lazyPagingItems.itemSnapshotList
                        .isEmpty()
                )
                    onNoData()
            }
        }
    }
}