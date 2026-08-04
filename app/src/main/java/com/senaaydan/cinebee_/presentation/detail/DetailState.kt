package com.senaaydan.cinebee_.presentation.detail

import com.senaaydan.cinebee_.domain.model.Movie

data class DetailState(
    val movie: Movie? = null,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val error: String?= null
)