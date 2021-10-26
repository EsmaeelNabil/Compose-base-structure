package com.esmaeel.composestructure.ui.movieDetails

import com.esmaeel.composestructure.base.ResultHandler
import com.esmaeel.composestructure.data.MoviesService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsUseCase @Inject constructor(
    private val api: MoviesService,
    private val resultHandler: ResultHandler
) {
    suspend fun invoke(id: String) = resultHandler.resultHandler {
        api.getMovieDetails(id)
    }
}