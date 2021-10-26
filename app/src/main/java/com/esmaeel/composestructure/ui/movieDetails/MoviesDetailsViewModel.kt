package com.esmaeel.composestructure.ui.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmaeel.composestructure.ContextProviders
import com.esmaeel.composestructure.base.StateContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesDetailsViewModel @Inject constructor(
    private val movieUseCase: MovieDetailsUseCase,
) : ViewModel() {

    suspend fun getMoviesListPager(id: String) = movieUseCase.invoke(id)

}

