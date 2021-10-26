package com.esmaeel.composestructure.ui.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.esmaeel.composestructure.data.MovieListResponse
import com.esmaeel.composestructure.data.MoviesService

class MoviesSource(private val api: MoviesService) : PagingSource<Int, MovieListResponse.Result>() {

    override fun getRefreshKey(state: PagingState<Int, MovieListResponse.Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieListResponse.Result> {
        return try {
            val currentLoadingPageKey = params.key ?: 1
            val response = api.getMovieList(currentLoadingPageKey)
            val nextKey = if (response.results.isEmpty()) null else currentLoadingPageKey.plus(1)
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            LoadResult.Page(
                data = response.results,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}