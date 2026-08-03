package com.senaaydan.cinebee_.presentation.home

import com.senaaydan.cinebee_.domain.model.Category
import com.senaaydan.cinebee_.domain.model.Movie

data class HomeState(
    val searchQuery : String = "",
    val categories : List<Category> = emptyList(),
    val isLoading : Boolean = false,
    val error : String? = null,
    val searchResults: List<Movie> = emptyList(),
)