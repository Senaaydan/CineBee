package com.senaaydan.cinebee_

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.senaaydan.cinebee_.presentation.home.HomeScreen
import com.senaaydan.cinebee_.ui.theme.CineBee_Theme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.senaaydan.cinebee_.presentation.detail.DetailScreen
import com.senaaydan.cinebee_.presentation.detail.DetailViewModel
import com.senaaydan.cinebee_.presentation.favorites.FavoritesScreen
import com.senaaydan.cinebee_.presentation.favorites.FavoritesState
import com.senaaydan.cinebee_.presentation.favorites.FavoritesViewModel
import com.senaaydan.cinebee_.presentation.home.HomeViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            CineBee_Theme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background)
                {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home"

                    ) {

                        composable("home") {
                            val viewModel: HomeViewModel = viewModel()
                            val state by viewModel.state.collectAsState()
                            HomeScreen(
                                state = state,
                                OnIntent = viewModel::onIntent,
                                MovieClick = {movie ->
                                    navController.navigate("detail/${movie.id}")
                                },
                                onFavoritesClick = {
                                    navController.navigate("favorites")
                                }
                            )
                        }

                        composable("detail/{movieId}") {backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
                            val viewModel : DetailViewModel = viewModel()
                            val state by viewModel.state.collectAsState()
                            // viewModel.loadDetail(1) denemek için koydum
                            if (movieId != null) {
                                DetailScreen(movieId = movieId,
                                state = state,onIntent = viewModel::onIntent)
                        }
                    }
                        composable("favorites") {
                            val viewModel: FavoritesViewModel = viewModel()
                            val state by viewModel.state.collectAsState()
                            FavoritesScreen(navController, state = state, onIntent = viewModel::onIntent)
                        }



                }
                }
                }
            }
        }
    }


