package com.senaaydan.cinebee_.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.senaaydan.cinebee_.core.navigation.NavigationEvent
import com.senaaydan.cinebee_.presentation.settings.AppStrings

@Composable
fun DetailRoute(
    movieId: Int,
    navController: NavController,
    strings: AppStrings,
    onTitleChanged: (String) -> Unit
) {

    val viewModel: DetailViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.loadDetail(movieId)
    }

    LaunchedEffect(state.movie?.title) {
        state.movie?.title?.let { title ->
            onTitleChanged(title)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->

            when (event) {

                is NavigationEvent.NavigateToFavorites -> {
                    navController.navigate("favorites")
                }

                else -> Unit
            }
        }
    }

    DetailScreen(
        movieId = movieId,
        state = state,
        onIntent = viewModel::onIntent,
        strings = strings
    )
}