package com.senaaydan.cinebee_.presentation.search

import android.R
import androidx.benchmark.traceprocessor.Row
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.senaaydan.cinebee_.ui.components.MovieCard

import com.senaaydan.cinebee_.ui.components.SearchBar
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.senaaydan.cinebee_.core.navigation.NavigationEvent
import com.senaaydan.cinebee_.presentation.settings.AppStrings
import com.senaaydan.cinebee_.ui.theme.CineBee_Theme


/*@Composable
fun SearchScreen(
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

}*/
@Composable
fun SearchScreen(
    state: MovieSearchState,
    onIntent: (MovieSearchIntent) -> Unit,
    strings: AppStrings
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        SearchBar(
            query = state.searchQuery,

            onQueryChange = { query ->
                onIntent(
                    MovieSearchIntent.SearchQueryChanged(query)
                )
            },

            placeholder = strings.searchPlaceholder,

            onSearch = {
                onIntent(
                    MovieSearchIntent.SearchSubmitted
                )
            },

            onSearchBarClick = {
                onIntent(
                    MovieSearchIntent.SearchBarClicked
                )
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                vertical = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (
                state.isSearchActive &&
                state.searchQuery.isBlank()
            ) {

                item {

                    RecentSearches(
                        searches = state.recentSearches,

                        onSearchClick = { query ->

                            onIntent(
                                MovieSearchIntent.SearchQueryChanged(
                                    query
                                )
                            )

                            onIntent(
                                MovieSearchIntent.SearchSubmitted
                            )
                        },

                        onDeleteClick = { query ->

                            onIntent(
                                MovieSearchIntent.DeleteRecentSearch(query)
                            )
                        },

                        onClearAllClick = {

                            onIntent(
                                MovieSearchIntent.ClearAllSearches
                            )
                        },
                        strings=strings
                    )
                }
            }


            else if (
                state.searchQuery.isNotBlank()
            ) {


                if (state.isLoading) {

                    item {

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {

                            CircularProgressIndicator()
                        }
                    }
                }

                else if (state.error != null) {

                    item {

                        Text(
                            text = state.error,
                            modifier = Modifier.padding(
                                horizontal = 16.dp
                            ),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }


                else if (
                    state.searchResults.isEmpty()
                ) {

                    item {

                        Text(
                            text = strings.movieNotFound,
                            modifier = Modifier.padding(
                                horizontal = 16.dp
                            ),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }


                else {

                    item {

                        Column(
                            modifier = Modifier.padding(
                                horizontal = 16.dp
                            )
                        ) {

                            Text(
                                text = strings.searchResult,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = "${state.searchResults.size} ${strings.movieFound}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    items(
                        items = state.searchResults,
                        key = { movie ->
                            movie.id
                        }
                    ) { movie ->

                        MovieCard(
                            movie = movie,

                            MovieClick = {

                                onIntent(
                                    MovieSearchIntent.MovieClicked(
                                        movie
                                    )
                                )
                            },

                            modifier = Modifier.padding(
                                horizontal = 16.dp
                            )
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun RecentSearches(
    searches: List<String>,
    onSearchClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    onClearAllClick: () -> Unit,
    strings: AppStrings
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = strings.recentSearches,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            if (searches.isNotEmpty()) {

                TextButton(
                    onClick = onClearAllClick
                ) {

                    Text(
                        text = strings.clearAll
                    )
                }
            }
        }

        if (searches.isEmpty()) {

            Text(
                text = strings.noSearchHistory,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        else {

            searches.forEach { query ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSearchClick(query)
                        }
                        .padding(
                            start = 16.dp,
                            end = 8.dp,
                            top = 4.dp,
                            bottom = 4.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )

                    Text(
                        text = query,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    IconButton(
                        onClick = {
                            onDeleteClick(query)
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Aramayı sil"
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
fun SearchScreenPreview() {
    CineBee_Theme {
        SearchScreen( state = MovieSearchState(), onIntent = {})
    }
}
*/



