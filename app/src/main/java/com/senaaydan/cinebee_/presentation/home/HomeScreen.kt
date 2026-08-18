@file:OptIn(ExperimentalMaterial3Api::class)

package com.senaaydan.cinebee_.presentation.home

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.senaaydan.cinebee_.core.navigation.NavigationEvent

import com.senaaydan.cinebee_.ui.components.CategoryRow
import com.senaaydan.cinebee_.ui.components.MovieCard
import com.senaaydan.cinebee_.ui.components.SearchBar
import com.senaaydan.cinebee_.data.local.DummyData
import com.senaaydan.cinebee_.domain.model.Category
import com.senaaydan.cinebee_.domain.model.Movie
import com.senaaydan.cinebee_.presentation.settings.AppStrings
import com.senaaydan.cinebee_.ui.theme.CineBee_Theme
import dagger.hilt.android.lifecycle.HiltViewModel
/*@Composable
fun HomeScreen(
    navController:NavController,strings: AppStrings
){

    val viewModel: HomeViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState()
    val navigationEvent = viewModel.navigationEvent

    LaunchedEffect(Unit) {
        navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.NavigateToFavorites -> {
                    navController.navigate("favorites")
                }

                is NavigationEvent.NavigateToDetail -> {
                    navController.navigate("detail/${event.movieId}")
                }

                else -> Unit

            }

        }
    }
    HomeScreen(
        state = state,
        OnIntent = viewModel::onIntent,
        strings=strings)
}
*/
@Composable
fun HomeScreen(
    state: HomeState,
    OnIntent: (HomeIntent) -> Unit = {},
    strings: AppStrings

    ) {


    Column(modifier = Modifier.fillMaxWidth()) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.error
                    )
                }
            }
            else ->
              LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.categories) { category ->
                    CategoryRow(
                        category = category,
                        MovieClick = { movie ->
                            OnIntent(HomeIntent.MovieClicked(movie))
                        }
                    )
                }
            }
        }
    }}



 /*   @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CineBee_Theme {
            HomeScreen(state = HomeState())
        }

    }*/

