package com.senaaydan.cinebee_.presentation.home

import androidx.lifecycle.ViewModel
import com.senaaydan.cinebee_.data.local.DummyData
import com.senaaydan.cinebee_.domain.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    private val allCategories : List<Category> = DummyData.categories

    init {
        loadMovies()
    }

    fun loadMovies() {
        _state.value = _state.value.copy(categories = allCategories)
    }


    fun onIntent(intent: HomeIntent) {
        when (intent){
            is HomeIntent.FavoritesClicked -> {
                TODO()
            }
            is HomeIntent.MovieClicked -> {
                TODO()
            }
            is HomeIntent.SearchQueryChanged -> {
                _state.value = _state.value.copy(searchQuery = intent.query)
                val filteredMovies = allCategories.flatMap { it.movies }.filter {
                    it.title.startsWith(intent.query, ignoreCase = true)
                }
                _state.value = _state.value.copy(searchResults = filteredMovies)
            }
        }

    }


}

