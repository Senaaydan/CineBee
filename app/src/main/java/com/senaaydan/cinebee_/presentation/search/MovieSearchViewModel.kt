package com.senaaydan.cinebee_.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senaaydan.cinebee_.core.navigation.NavigationEvent
import com.senaaydan.cinebee_.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(private val repository: MovieRepository): ViewModel()
{
  private val _state = MutableStateFlow(MovieSearchState())
    val state = _state.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private fun saveSearch(){
        viewModelScope.launch {
            repository.saveSearch(_state.value.searchQuery)
        }
    }
    init {
        loadRecentSearches()
    }
    private fun loadRecentSearches() {
        viewModelScope.launch {
            repository.getRecentSearches().collect { searches ->
                _state.value = _state.value.copy(
                    recentSearches = searches
                )
            }
        }
    }

     fun onIntent(intent: MovieSearchIntent) {

            when (intent) {

                is MovieSearchIntent.SearchBarClicked -> {
                    SearchBarClicked()
                }

                is MovieSearchIntent.SearchQueryChanged -> {

                    SearchQueryChanged(intent.query)
                }

                is MovieSearchIntent.SearchSubmitted -> {

                    SearchSubmitted()
                }

                is MovieSearchIntent.MovieClicked -> {

                    MovieClicked(intent.movie.id)
                }

                is MovieSearchIntent.ClearSearch -> {
                    ClearSearch()
                }

                is MovieSearchIntent.DeleteRecentSearch -> {

                   DeleteRecentSearch(intent.query)
                }

                is MovieSearchIntent.ClearAllSearches -> {
                    ClearAllSearches()
                }
            }
        }



    private fun SearchBarClicked(){
        _state.value = _state.value.copy(
            isSearchActive = true
        )

    }
   private fun SearchQueryChanged(query: String) {
       _state.value = _state.value.copy(
           searchQuery = query,

       )

       if (query.isBlank()) {
           _state.value = _state.value.copy(
               searchResults = emptyList(),
               isLoading = false,
               error = null
           )
           return
       }

       viewModelScope.launch {

           _state.value = _state.value.copy(
               isLoading = true,
               error = null
           )

           try {
               val results = repository.searchMovies(query)

               _state.value = _state.value.copy(
                   searchResults = results,
                   isLoading = false
               )

           } catch (e: Exception) {

               _state.value = _state.value.copy(
                   searchResults = emptyList(),
                   isLoading = false,
                   error = e.message ?: "Arama sırasında bir hata oluştu."
               )
           }
       }
   }
    private fun SearchSubmitted(){
        val query = _state.value.searchQuery

        if (query.isNotBlank()) {

            viewModelScope.launch {
                repository.saveSearch(query)
            }
        }

    }
    private fun MovieClicked(movieId: Int){
        viewModelScope.launch {

            _navigationEvent.emit(
                NavigationEvent.NavigateToDetail(
                    movieId = movieId
                )
            )
        }

    }
    private fun ClearSearch(){

        _state.value = _state.value.copy(
            searchQuery = "",
            searchResults = emptyList(),
            isSearchActive = true
        )

    }
    private fun DeleteRecentSearch(query: String){
        viewModelScope.launch {

            repository.deleteRecentSearch(query)
        }

    }
    private fun ClearAllSearches(){
        viewModelScope.launch {
            repository.clearAllSearches()
        }
    }

    }

