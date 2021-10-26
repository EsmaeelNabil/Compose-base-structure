package com.esmaeel.composestructure.ui.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.esmaeel.composestructure.data.MoviesService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesListUseCase @Inject constructor(private val api: MoviesService) {
    fun invoke() = Pager(PagingConfig(pageSize = 20,prefetchDistance = 1)) {
        MoviesSource(api = api)
    }
}