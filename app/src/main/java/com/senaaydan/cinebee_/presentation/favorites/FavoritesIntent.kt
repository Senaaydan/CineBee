package com.senaaydan.cinebee_.presentation.favorites

import com.senaaydan.cinebee_.domain.model.Movie

sealed class FavoritesIntent {
    data class SearchQueryChanged(val query: String) : FavoritesIntent()
    data class RemoveFavoriteClicked(val movieId: Int) : FavoritesIntent()
    data class MovieClicked(val movieId:Int) : FavoritesIntent()
    object BrowseMoviesClicked : FavoritesIntent()
}
