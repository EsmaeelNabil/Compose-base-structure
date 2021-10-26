package com.esmaeel.composestructure.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/now_playing")
    suspend fun getMovieList(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = "ebea8cfca72fdff8d2624ad7bbf78e4c",
    ): MovieListResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movie: String = "482321",
        @Query("api_key") search: String = "ebea8cfca72fdff8d2624ad7bbf78e4c",
    ): MovieDetailResponse
}


