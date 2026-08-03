package com.senaaydan.cinebee_.presentation.home

import com.senaaydan.cinebee_.domain.model.Movie
import java.util.Objects

sealed class HomeIntent (){


    object FavoritesClicked : HomeIntent()
    data class MovieClicked(val movie: Movie) : HomeIntent()
    data class SearchQueryChanged(val query: String) : HomeIntent()
}


