package com.senaaydan.cinebee_.presentation.search

import com.senaaydan.cinebee_.domain.model.Movie

sealed class MovieSearchIntent {

    data class SearchQueryChanged(val query: String) : MovieSearchIntent()
    data class MovieClicked(val movie: Movie) : MovieSearchIntent()
    object ClearSearch : MovieSearchIntent()
    object ClearAllSearches : MovieSearchIntent()
    data class DeleteRecentSearch(val query: String) : MovieSearchIntent()
    object SearchSubmitted : MovieSearchIntent()
    object SearchBarClicked : MovieSearchIntent()


}