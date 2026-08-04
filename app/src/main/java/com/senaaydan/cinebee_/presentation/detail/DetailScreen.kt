package com.senaaydan.cinebee_.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(movieId: Int,state: DetailState,onIntent: (DetailIntent) -> Unit ){
    val movie=state.movie

    if (movie == null) {
        Text("Film bulunamadı.")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.background(color = Color.Yellow),
                title = {
                    Text(
                        text = movie.title,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Black
                    )
                },
                actions = {
                    IconButton(
                        onClick = { onIntent(DetailIntent.FavoritesClicked)  },

                        )
                    {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Bookmark",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(30.dp)


                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )

        }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),

            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Card(modifier = Modifier.fillMaxWidth().height(300.dp)) {

                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
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
                            contentDescription = "IMDdb",
                            tint = Color.Yellow
                        )
                        Text(text = " ${movie.imdb}", color = Color.White)
                    }
                }

            }
            Column(modifier = Modifier.fillMaxSize()) {


                Text(text = "${movie.year} • ${movie.duration}")
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = movie.cast.joinToString(", ") { actor -> actor.name },
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = TextUnit.Unspecified,
                        fontFamily = FontFamily.SansSerif,
                        style = MaterialTheme.typography.bodyLarge,

                        )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = movie.description,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = TextUnit.Unspecified
                    )
                }


            }


        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    CineBee_Theme {
        DetailScreen(
            movieId = 1,
            state = DetailState(),
            onIntent = {}
        )
    }

}




