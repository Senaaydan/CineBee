package com.senaaydan.cinebee_.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.senaaydan.cinebee_.data.local.DummyData
import com.senaaydan.cinebee_.ui.theme.CineBee_Theme

import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import coil.compose.AsyncImage
import com.senaaydan.cinebee_.ui.components.ActorCard
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.senaaydan.cinebee_.core.navigation.NavigationEvent
import com.senaaydan.cinebee_.presentation.settings.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun DetailScreen(movieId: Int,state: DetailState,onIntent: (DetailIntent) -> Unit,strings: AppStrings
                  ){
    val movie=state.movie

    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }

        state.movie == null -> {
            Text(strings.movieNotFound)
        }

        else -> {


            Column(
                modifier = Modifier.fillMaxSize(),

                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                Card(modifier = Modifier.fillMaxWidth().height(400.dp)) {

                    Box(

                        modifier = Modifier.fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.background)
                    ) {
                        AsyncImage(
                            model = state.movie?.imageUrl,
                            contentDescription = state.movie?.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                        IconButton(
                            onClick = {
                                onIntent(DetailIntent.ToggleFavoritesClicked)

                            },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorite",
                                tint = if (state.isFavorite) Color.Red else Color.White
                            )
                        }
                        Row(
                            modifier = Modifier.padding(10.dp).align(Alignment.BottomStart)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "IMDb",
                                tint = Color.Yellow
                            )
                            Text(text = " ${String.format("%.1f", movie?.imdb ?: 0.0)}", color = Color.White)
                        }
                    }

                }
                Column(modifier = Modifier.fillMaxSize()) {


                    Text(text = "${movie?.year} • ${movie?.duration}")
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = strings.cast,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyRow(
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        )
                        {
                            items(state.movie?.cast ?: emptyList()) { actor ->
                                ActorCard(actor = actor)
                            }

                        }


                        Text(
                            text = movie?.description.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = TextUnit.Unspecified
                        )
                    }


                }


            }

        }
    }}
/*
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    CineBee_Theme {
        DetailScreen(
            movieId = 1,
            state = DetailState(),
            onIntent = {},

        )
    }

}*/




