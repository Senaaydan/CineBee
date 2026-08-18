package com.senaaydan.cinebee_.presentation.favorites

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.senaaydan.cinebee_.ui.theme.CineBee_Theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.senaaydan.cinebee_.ui.components.FavoriteMovieCard
import com.senaaydan.cinebee_.domain.model.Movie
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.senaaydan.cinebee_.ui.components.SearchBar
import com.senaaydan.cinebee_.data.local.DummyData.categories
import com.senaaydan.cinebee_.presentation.settings.AppStrings



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController,state: FavoritesState,onIntent :(FavoritesIntent) -> Unit , strings: AppStrings) {


   /* if (state.favoriteMovies.isEmpty()) {
        EmptyFavoritesScreen(navController, onIntent= onIntent,strings=strings)
    } else {
        FavoriteList(state=state,onIntent= onIntent,strings=strings)
    }*/


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
                    text = state.error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        state.favoriteMovies.isEmpty() -> {
            EmptyFavoritesScreen(
                navController = navController,
                onIntent = onIntent,
                strings = strings
            )
        }

        else -> {
            FavoriteList(
                state = state,
                onIntent = onIntent,
                strings = strings
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyFavoritesScreen(
    navController: NavController,onIntent :(FavoritesIntent) -> Unit = {},strings: AppStrings
) {

        Column(

            modifier = Modifier
                .fillMaxSize(),
                //.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = strings.favoritesEmpty, fontWeight = FontWeight.W600)
            Spacer(modifier = Modifier.height(8.dp))

            Text( text =  strings.heartClick, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic )
            Spacer(modifier = Modifier.height(16.dp))
            Column()
            {
                IconButton(onClick = { onIntent(FavoritesIntent.BrowseMoviesClicked) }, modifier = Modifier
                    .size(60.dp)
                    .align(alignment = Alignment.CenterHorizontally)) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.Movie,
                        contentDescription = "Heart Broken",
                        tint = Color.LightGray

                    )
                }

                Text(text =  strings.discover, textAlign = TextAlign.Center)

            }
        }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteList(state: FavoritesState,onIntent: (FavoritesIntent) -> Unit,strings: AppStrings) {



        Column(modifier = Modifier.fillMaxWidth()) {
            SearchBar(query = state.searchQuery, onQueryChange = { onIntent(FavoritesIntent.SearchQueryChanged(it))  }, placeholder = "Favorilerimde Ara...", onSearch = {})
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),

                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (state.searchQuery.isBlank()) {

                    items(state.favoriteMovies) { movie ->
                        FavoriteMovieCard(
                            movie = movie,
                            onMovieClick = {
                                onIntent(FavoritesIntent.MovieClicked(movie.id))
                            },
                            onRemoveClick = {
                              onIntent(FavoritesIntent.RemoveFavoriteClicked(movie.id))
                            }
                        )
                    }

                } else {

                    if (state.filteredFavorites.isEmpty()) {

                        item {
                            Column(
                                modifier = Modifier.fillParentMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                Icon(
                                    imageVector = Icons.Default.SearchOff,
                                    contentDescription = null,
                                    modifier = Modifier.size(70.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = strings.movieNotFound,
                                    style = MaterialTheme.typography.titleLarge
                                )


                                    Text(
                                        text = strings.favoriteNotFoundWithQuery.format(state.searchQuery),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                    } else {

                        item {

                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {

                                Text(
                                    text = strings.searchResult,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "${state.filteredFavorites.size} ${strings.movieFound}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }

                        items(state.filteredFavorites) { movie ->

                            FavoriteMovieCard(
                                movie = movie,
                                onMovieClick = {
                                    onIntent(FavoritesIntent.MovieClicked(movie.id))
                                },
                                onRemoveClick = {
                                    onIntent(FavoritesIntent.RemoveFavoriteClicked(movie.id))

                                }
                            )

                        }
                    }
                }
                }


            }

    }















/*
@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    CineBee_Theme {
        EmptyFavoritesScreen(
            NavController(LocalContext.current),

        )
    }

}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPrevieww() {
    CineBee_Theme {
        FavoriteList( onIntent = {}, state = FavoritesState())
    }

}
*/