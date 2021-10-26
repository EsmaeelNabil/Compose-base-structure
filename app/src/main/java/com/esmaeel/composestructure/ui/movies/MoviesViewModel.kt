package com.esmaeel.composestructure.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.esmaeel.composestructure.data.MovieDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesListUseCase,
) : ViewModel() {

    fun getMoviesListPager() = moviesUseCase.invoke().flow.cachedIn(viewModelScope)

}

