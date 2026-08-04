package com.senaaydan.cinebee_.presentation.detail

import androidx.lifecycle.ViewModel
import com.senaaydan.cinebee_.data.local.DummyData
import com.senaaydan.cinebee_.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class DetailViewModel : ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()
    val allMovies: List<Movie> = DummyData.categories.flatMap { it.movies }


    /* init {
        loadDetail(movieId : Int)
    }
*/

    fun loadDetail(movieId: Int) {
        _state.value = _state.value.copy(movie = DummyData.getMovieId(movieId))
    }


    fun onIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.FavoritesClicked -> Unit
            is DetailIntent.ToggleFavoritesClicked -> {
                if (_state.value.isFavorite == false) {
                    _state.value = _state.value.copy(isFavorite = true)
                } else {
                    _state.value = _state.value.copy(isFavorite = false)
                }
            }
        }
    }

}
