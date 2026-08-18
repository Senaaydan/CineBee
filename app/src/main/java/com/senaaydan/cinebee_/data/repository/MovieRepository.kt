package com.senaaydan.cinebee_.data.repository

import com.senaaydan.cinebee_.domain.model.Category
import com.senaaydan.cinebee_.domain.model.Movie
import kotlinx.coroutines.flow.Flow


interface MovieRepository{
   suspend fun getCategories(): List<Category>
   suspend fun getMovieById(movieId: Int): Movie?
   suspend fun searchMovies(query: String): List<Movie>
   suspend fun removeFavorite(movieId: Int)
   suspend fun getFavorite(movieId: Int): Movie?
   suspend fun addFavorite(movie: Movie)
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun saveSearch(searchQuery: String)
    suspend fun clearAllSearches()
   suspend fun deleteRecentSearch(query: String)
   suspend fun getRecentSearches(): Flow<List<String>>


}