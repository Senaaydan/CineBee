package com.senaaydan.cinebee_.presentation.favorites

import androidx.lifecycle.ViewModel
import com.senaaydan.cinebee_.data.local.DummyData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.senaaydan.cinebee_.domain.model.Movie
import com.senaaydan.cinebee_.presentation.home.HomeIntent
import com.senaaydan.cinebee_.presentation.home.HomeScreen
import com.senaaydan.cinebee_.presentation.home.HomeState

class FavoritesViewModel : ViewModel() {
    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()
    private val allFavoriteMovies: List<Movie> = DummyData.categories.flatMap { it.movies }

    init {
        loadFavoriteMovies()
    }

    fun loadFavoriteMovies() {
        _state.value = _state.value.copy(favoriteMovies = allFavoriteMovies)
        _state.value = _state.value.copy(filteredFavorites = allFavoriteMovies)
        } // emin değilim



    fun onIntent(intent: FavoritesIntent){
        when(intent){
            is FavoritesIntent.BrowseMoviesClicked -> Unit
             // Navigate kurarken doldurulacak

            is FavoritesIntent.MovieClicked -> Unit



            is FavoritesIntent.RemoveFavoriteClicked -> {
                val updatedFavorites = _state.value.favoriteMovies.filter { it.id != intent.movieId }
                _state.value = _state.value.copy(favoriteMovies = updatedFavorites)
                _state.value = _state.value.copy(filteredFavorites = updatedFavorites)
            }
            is FavoritesIntent.SearchQueryChanged -> {
                _state.value = _state.value.copy(searchQuery = intent.query)
                val filteredFavorites = state.value.favoriteMovies.filter {
                    it.title.startsWith(intent.query, ignoreCase = true)
                }
                _state.value = _state.value.copy(filteredFavorites = filteredFavorites)

            }
        }

    }
    }


