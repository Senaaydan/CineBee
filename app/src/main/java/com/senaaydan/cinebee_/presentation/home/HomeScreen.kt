@file:OptIn(ExperimentalMaterial3Api::class)

package com.senaaydan.cinebee_.presentation.home

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight

import com.senaaydan.cinebee_.ui.components.CategoryRow
import com.senaaydan.cinebee_.ui.components.MovieCard
import com.senaaydan.cinebee_.ui.components.SearchBar
import com.senaaydan.cinebee_.data.local.DummyData
import com.senaaydan.cinebee_.domain.model.Category
import com.senaaydan.cinebee_.domain.model.Movie
import com.senaaydan.cinebee_.ui.theme.CineBee_Theme




@Composable
fun HomeScreen(
    state: HomeState,
    OnIntent :(HomeIntent) -> Unit = {},
    MovieClick : (Movie) -> Unit = {},
    onFavoritesClick: () -> Unit = {}
){
   /* var searchQuery :String by remember { mutableStateOf("") }
    val movieList = categories.flatMap { it.movies }
    val filteredMovies = movieList.filter {
        it.title.startsWith(searchQuery, ignoreCase = true)
    }*/

    Scaffold(

        topBar = {

            TopAppBar(

                actions = {
                    IconButton(onClick = { onFavoritesClick() }) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Bookmark",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )

                    }
                },
                title = { Text(text = "CineBee") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )

            )



        }

    )
    { paddingValues ->
        Column(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {

            SearchBar(query = state.searchQuery, onQueryChange = {OnIntent(HomeIntent.SearchQueryChanged(it))}, placeholder = "Film Ara..." )
            LazyColumn(modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {
                if (state.searchQuery.isBlank()) {
                        items(state.categories) { category ->
                            CategoryRow(
                                category = category,
                                MovieClick = MovieClick
                            )
                        }
                    } else {
                        if (state.searchResults.isEmpty()){
                            item {
                                Text(text = "Film bulunamadı")
                            }
                        }
                    else {
                            item {

                                Column(
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                ) {

                                    Text(
                                        text = "Arama Sonuçları",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "${state.searchResults.size} film bulundu",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))
                                }

                            }


                            items(state.searchResults) { movie ->

                                MovieCard(
                                    movie = movie,
                                    MovieClick = MovieClick,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )

                            }
                        }

                    }
                }
            }
        }

    }



    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CineBee_Theme {
            HomeScreen(state = HomeState(), MovieClick = {}, onFavoritesClick = {})
        }

    }

