package com.senaaydan.cinebee_.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.senaaydan.cinebee_.core.navigation.NavigationEvent
import com.senaaydan.cinebee_.presentation.settings.AppStrings

@Composable
fun SearchRoute(
    navController: NavController,strings: AppStrings
){
    val viewModel: MovieSearchViewModel =
        hiltViewModel()

    val state by
    viewModel.state.collectAsState()

    val navigationEvent =
        viewModel.navigationEvent
    LaunchedEffect(Unit) {

        navigationEvent.collect { event ->

            when (event) {

                is NavigationEvent.NavigateToDetail -> {

                    navController.navigate(
                        "detail/${event.movieId}"
                    )
                }

                else -> Unit
            }
        }
    }
    SearchScreen(
        state =state,
        onIntent = viewModel::onIntent,
        strings = strings
    )

}