package com.senaaydan.cinebee_.presentation.detail

sealed class DetailIntent{
    object FavoritesClicked : DetailIntent()
    object ToggleFavoritesClicked : DetailIntent()
}