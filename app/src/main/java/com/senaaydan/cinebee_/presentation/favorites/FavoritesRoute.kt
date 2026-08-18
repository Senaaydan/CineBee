package com.senaaydan.cinebee_.presentation.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.senaaydan.cinebee_.core.navigation.NavigationEvent
import com.senaaydan.cinebee_.presentation.settings.AppStrings


@Composable
fun FavoritesRoute(navController: NavController,strings: AppStrings){
val viewModel: FavoritesViewModel = hiltViewModel()

val state by viewModel.state.collectAsState()
val navigationEvent = viewModel.navigationEvent



LaunchedEffect(Unit) {
    navigationEvent.collect { event ->
        when (event) {
            is NavigationEvent.NavigateToHome -> {
                navController.navigate("home")
            }

            is NavigationEvent.NavigateToDetail -> {
                navController.navigate("detail/${event.movieId}")
            }

            else -> Unit
        }
    }


}
    FavoritesScreen(navController = navController, state = state, onIntent = viewModel::onIntent, strings=strings)
}