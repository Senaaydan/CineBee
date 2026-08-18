package com.senaaydan.cinebee_.data.local

import com.senaaydan.cinebee_.domain.model.Actor
import com.senaaydan.cinebee_.domain.model.Category
import com.senaaydan.cinebee_.domain.model.Movie

object DummyData {
    fun getMovieId(id: Int): Movie? {
        return categories.flatMap { it.movies }.find { it.id == id }

    }

    fun getMovies() {
        TODO("Not yet implemented")
    }

    val categories = listOf(
        Category(
            id = 1,
            title = "Popüler Filmler",
            movies = listOf(
                Movie(
                    1, "Inception", "https://via.placeholder.com/150",
                    imdb = 8.8, year = "2010", "148 dk", description = "film konusu", genre = "aksiyon", cast = listOf(
                        Actor("Leonardo DiCaprio", "Cobb", "https://via.placeholder.com/150"),
                        Actor("Joseph Gordon-Levitt", "Arthur", "https://via.placeholder.com/150"),
                    )
                ),
                Movie(2, "Interstellar", "https://via.placeholder.com/150", genre = "aksiyon"),
                Movie(3, "The Dark Knight", "https://via.placeholder.com/150", genre = "aksiyon"),
                Movie(4, "Oppenheimer", "https://via.placeholder.com/150", genre = "aksiyon")
            )
        ),
        Category(
            id = 2,
            title = "Aksiyon Filmleri",
            movies = listOf(
                Movie(8, "The Matrix", "https://via.placeholder.com/150", genre = "aksiyon"),
                Movie(9, "Blade Runner 2049", "https://via.placeholder.com/150", genre = "aksiyon")
            )
        ),
        Category(
            id = 3,
            title = "Bilim Kurgu",
            movies = listOf(
                Movie(8, "The Matrix", "https://via.placeholder.com/150", genre = "aksiyon"),
                Movie(9, "Blade Runner 2049", "https://via.placeholder.com/150", genre = "aksiyon")
            )
        )
    )
}