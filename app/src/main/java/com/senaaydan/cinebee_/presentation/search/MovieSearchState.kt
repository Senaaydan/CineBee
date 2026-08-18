package com.senaaydan.cinebee_.presentation.search

import com.senaaydan.cinebee_.domain.model.Movie

data class MovieSearchState(
    val searchQuery: String = "",
    val searchResults: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val recentSearches: List<String> = emptyList(),
    val isSearchActive: Boolean = false
)
