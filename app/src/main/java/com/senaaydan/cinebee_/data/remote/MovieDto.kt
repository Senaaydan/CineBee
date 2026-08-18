package com.senaaydan.cinebee_.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "vote_average")
    val voteAverage: Double = 0.0,
    val runtime: Int? = null,
    @Json(name = "genre_ids")
    val genreIds: List<Int> = emptyList(),
    @Json(name="genres")
    val genres: List<GenreDto> = emptyList()
)
@JsonClass(generateAdapter = true)
data class GenreDto(
    val id: Int,
    val name: String
)