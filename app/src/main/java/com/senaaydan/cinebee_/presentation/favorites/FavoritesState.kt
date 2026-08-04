package com.senaaydan.cinebee_.presentation.favorites

import com.senaaydan.cinebee_.domain.model.Movie

data class FavoritesState(
    val favoriteMovies: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val filteredFavorites: List<Movie> = emptyList(),
    val isLoading : Boolean = false,
    val error : String? =null
)
