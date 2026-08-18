package com.senaaydan.cinebee_.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senaaydan.cinebee_.core.navigation.NavigationEvent
import com.senaaydan.cinebee_.data.local.DummyData
import com.senaaydan.cinebee_.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.senaaydan.cinebee_.domain.model.Movie
import com.senaaydan.cinebee_.presentation.home.HomeIntent
import com.senaaydan.cinebee_.presentation.home.HomeScreen
import com.senaaydan.cinebee_.presentation.home.HomeState
import com.senaaydan.cinebee_.presentation.search.MovieSearchIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        loadFavoriteMovies()
    }

    fun loadFavoriteMovies() {
        viewModelScope.launch {

            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            try {
                repository.getFavoriteMovies().collect { favoriteMovies ->

                    _state.value = _state.value.copy(
                        favoriteMovies = favoriteMovies,
                        filteredFavorites = favoriteMovies,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {

                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Favoriler yüklenirken bir hata oluştu."
                )
            }
        }
    }


    fun onIntent(intent: FavoritesIntent){
        when(intent){

            is FavoritesIntent.BrowseMoviesClicked -> {
                BrowseMoviesClicked()
            }


            is FavoritesIntent.MovieClicked -> {
               MovieClicked(intent.movieId)
            }



            is FavoritesIntent.RemoveFavoriteClicked -> {
               RemoveFavoriteClicked(intent.movieId)
            }
            is FavoritesIntent.SearchQueryChanged -> {
                SearchQueryChanged(intent.query)
            }
        }

    }

    private fun BrowseMoviesClicked(){
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToHome)
        }

    }

    private fun MovieClicked(movieId: Int){
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToDetail(movieId))
        }

    }
    private fun RemoveFavoriteClicked(movieId: Int){
        viewModelScope.launch {
            repository.removeFavorite(movieId)
        }

    }
    private fun SearchQueryChanged(query: String){
        _state.value = _state.value.copy(searchQuery = query)
        val filteredFavorites = state.value.favoriteMovies.filter {
            it.title.startsWith(query, ignoreCase = true)
        }
        _state.value = _state.value.copy(filteredFavorites = filteredFavorites)

    }

}


