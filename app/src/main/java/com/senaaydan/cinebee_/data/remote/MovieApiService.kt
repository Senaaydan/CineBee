package com.senaaydan.cinebee_.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApiService {
    @GET("3/movie/popular")
    suspend fun getPopularMovies(): MovieResponseDto // ana threadi bloke etmemesi için suspend fonk

    @GET("3/search/movie")
    suspend fun searchMovies(
        @Query("query") query: String
    ): MovieResponseDto

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): MovieDto

    //kategorileri tek tek almak yerine bu şekilde aldık
    @GET("3/discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") genreId: Int,
        @Query("language") language: String
    ): MovieResponseDto


    @GET("3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
        ): CreditsResponseDto
}

