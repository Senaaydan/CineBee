package com.senaaydan.cinebee_.data.mapper

import com.senaaydan.cinebee_.data.local.entity.FavoriteMovieEntity
import com.senaaydan.cinebee_.domain.model.Movie

fun Movie.toFavoriteMovieEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        imdb = imdb,
        year = year,
        duration = duration,
        description = description,
        genre = genre
    )
}

fun FavoriteMovieEntity.toMovie(): Movie{
    return Movie(
        id = id,
        title = title,
        imageUrl = imageUrl,
        imdb = imdb,
        year = year,
        duration = duration,
        description = description,
        genre=genre
    )
}
fun genreName(id: Int): String {
    return when (id) {
        28 -> "Action"
        35 -> "Comedy"
        18 -> "Drama"
        878 -> "Science Fiction"
        27 -> "Horror"
        10749 -> "Romance"
        else -> ""
    }
}

