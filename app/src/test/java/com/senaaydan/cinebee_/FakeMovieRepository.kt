package com.senaaydan.cinebee_

import kotlinx.coroutines.flow.Flow
import com.senaaydan.cinebee_.data.repository.MovieRepository
import com.senaaydan.cinebee_.domain.model.Category
import com.senaaydan.cinebee_.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow

class FakeMovieRepository : MovieRepository {

    var categories: List<Category> = emptyList()

    var movieById: Movie? = null

    var searchResult: List<Movie> = emptyList()
    var savedSearchQuery: String? = null
    var deletedSearchQuery: String? = null
    var removedFavoriteId: Int? = null

    private val favoriteMoviesFlow =
        MutableStateFlow<List<Movie>>(emptyList())

    private val recentSearchesFlow =
        MutableStateFlow<List<String>>(emptyList())


    override suspend fun getCategories(): List<Category> {
        return categories
    }


    override suspend fun getMovieById(
        movieId: Int
    ): Movie? {
        return movieById
    }


    override suspend fun searchMovies(
        query: String
    ): List<Movie> {
        return searchResult
    }


    override suspend fun addFavorite(
        movie: Movie
    ) {
        favoriteMoviesFlow.value =
            favoriteMoviesFlow.value + movie
    }


    override suspend fun removeFavorite(
        movieId: Int
    ) {
        removedFavoriteId=movieId
        favoriteMoviesFlow.value =
            favoriteMoviesFlow.value.filter { movie ->
                movie.id != movieId
            }
    }


    override suspend fun getFavorite(
        movieId: Int
    ): Movie? {
        return favoriteMoviesFlow.value.find { movie ->
            movie.id == movieId
        }
    }


    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return favoriteMoviesFlow
    }


    override suspend fun saveSearch(
        searchQuery: String
    ) {
        savedSearchQuery=searchQuery
        recentSearchesFlow.value =
            listOf(searchQuery) +
                    recentSearchesFlow.value.filter {
                        it != searchQuery
                    }
    }


    override suspend fun deleteRecentSearch(
        query: String
    ) {
        deletedSearchQuery=query
        recentSearchesFlow.value =
            recentSearchesFlow.value.filter {
                it != query
            }
    }


    override suspend fun clearAllSearches() {
        recentSearchesFlow.value = emptyList()
    }


    override suspend fun getRecentSearches(): Flow<List<String>> {
        return recentSearchesFlow
    }

    fun setFavoriteMovies(movies: List<Movie>) {
        favoriteMoviesFlow.value = movies
    }

}