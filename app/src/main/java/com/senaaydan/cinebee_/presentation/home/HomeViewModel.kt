package com.senaaydan.cinebee_.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senaaydan.cinebee_.core.navigation.NavigationEvent
import com.senaaydan.cinebee_.data.local.DummyData
import com.senaaydan.cinebee_.data.local.DummyData.categories
import com.senaaydan.cinebee_.data.repository.MovieRepository
import com.senaaydan.cinebee_.domain.model.Category
import com.senaaydan.cinebee_.presentation.favorites.FavoritesIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository): ViewModel()
{
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()

    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {

            _state.value = _state.value.copy(
                categories = categories,
                isLoading = true,
                error = null
            )

            try {

                val categories = repository.getCategories()
                _state.value = _state.value.copy(
                    categories = categories,
                    isLoading = false
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Bir hata oluştu"
                )
            }
        }
    }



    fun onIntent(intent: HomeIntent) {
        when (intent){
            is HomeIntent.FavoritesClicked -> {
                FavoritesClicked()
            }


            is HomeIntent.MovieClicked -> {
                MovieClicked(intent.movie.id)
            }


            is HomeIntent.SearchQueryChanged -> {
              SearchQueryChanged(intent.query)
            }
        }

    }

    private fun FavoritesClicked(){
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToFavorites)
        }

    }
    private fun MovieClicked(movieId: Int){
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToDetail(movieId))
        }

    }
    private fun SearchQueryChanged(query: String){
        _state.value = _state.value.copy(searchQuery = query)
        viewModelScope.launch {
            val filteredMovies =
                repository.searchMovies(query)

            _state.value = _state.value.copy(
                searchResults = filteredMovies
            )
        }

    }


}

