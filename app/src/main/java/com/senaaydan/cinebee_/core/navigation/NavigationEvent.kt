package com.senaaydan.cinebee_.core.navigation

sealed class NavigationEvent{
    object NavigateToFavorites : NavigationEvent()
    object NavigateToHome : NavigationEvent()
    object NavigateBack : NavigationEvent()
    data class NavigateToDetail(val movieId : Int) : NavigationEvent()
}
