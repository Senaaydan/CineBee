package com.senaaydan.cinebee_.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senaaydan.cinebee_.core.navigation.NavigationEvent
import com.senaaydan.cinebee_.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun loadDetail(movieId: Int) {
        viewModelScope.launch {
            _state.value =_state.value.copy(
                isLoading = true
            )
            val movie = repository.getMovieById(movieId)
            val favoriteMovie = repository.getFavorite(movieId)

            _state.value = _state.value.copy(
                movie = movie,
                isLoading = false,
                isFavorite = favoriteMovie != null
            )
        }
    }


    fun onIntent(intent: DetailIntent) {
        when (intent) {

            is DetailIntent.FavoritesClicked -> {
               FavoritesClicked()
            }

            is DetailIntent.ToggleFavoritesClicked -> {
                 ToogleFavoritesClicked()
        }
    }

}



private fun FavoritesClicked(){
    viewModelScope.launch {
        _navigationEvent.emit(NavigationEvent.NavigateToFavorites)
    }
}

    private fun ToogleFavoritesClicked(){
        val movie = _state.value.movie
        if(movie != null){
            viewModelScope.launch {
                if(_state.value.isFavorite){
                    repository.removeFavorite(movie.id)
                    _state.value = _state.value.copy(isFavorite = false)
                }else{
                    repository.addFavorite(movie)
                    _state.value = _state.value.copy(isFavorite = true)
                }
            }


        }

    }









}
