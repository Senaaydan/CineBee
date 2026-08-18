package com.senaaydan.cinebee_.data.mapper



import com.senaaydan.cinebee_.data.remote.MovieDto
import com.senaaydan.cinebee_.domain.model.Movie

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        imageUrl = if (posterPath != null) {
            "https://image.tmdb.org/t/p/w500$posterPath"
        } else {
            ""
        },
        imdb = voteAverage,
        year = releaseDate.take(4),
        description = overview,
        genre = if (genres.isNotEmpty()) {

            genres.joinToString(", ") { genre ->
                genre.name
            }

        } else {

            genreIds
                .mapNotNull { genreId ->

                    when (genreId) {
                        28 -> "Action"
                        35 -> "Comedy"
                        18 -> "Drama"
                        878 -> "Science Fiction"
                        27 -> "Horror"
                        10749 -> "Romance"
                        else -> null
                    }
                }
                .joinToString(", ")
        },
        duration = if (runtime != null) {
            "$runtime dk"
        } else {
            ""
        }
    )
}