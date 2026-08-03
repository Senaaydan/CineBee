package com.senaaydan.cinebee_.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.senaaydan.cinebee_.ui.components.SearchBar
import com.senaaydan.cinebee_.data.local.DummyData.categories


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController) {
    val favoriteList = categories.flatMap { it.movies }


    if (favoriteList.isEmpty()) {
        EmptyFavoritesScreen(navController)
    } else {
        FavoriteList(navController)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyFavoritesScreen(navController: NavController) {


    Scaffold(

        topBar = {

            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Bookmark",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )


                    }
                },

                title = { Text(text = "Favorilerim ") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )


            )

        }
    )
    { paddingValues ->
        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Favori listesi boş", fontWeight = FontWeight.W600)
            Spacer(modifier = Modifier.height(8.dp))

            Text( text =  "Favorilerinizi kalp ikonuna tıklayarak ekleyebilirsiniz.", fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic )
            Spacer(modifier = Modifier.height(16.dp))
            Column()
            {
                IconButton(onClick = { navController.navigate("home") }, modifier = Modifier
                    .size(60.dp)
                    .align(alignment = Alignment.CenterHorizontally)) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.Movie,
                        contentDescription = "Heart Broken",
                        tint = Color.LightGray

                    )
                }

                Text(text =  "Filmleri keşfet!", textAlign = TextAlign.Center)

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteList(navController: NavController,
                 favoriteList: List<Movie> = categories.flatMap { it.movies }) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredFavorites = favoriteList.filter {
        it.title.startsWith(searchQuery, ignoreCase = true)
    }


    Scaffold(

        topBar = {

            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Bookmark",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )


                    }
                },

                title = { Text(text = "Favorilerim ") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )


            )



        }
    )
    {

            paddingValues ->
        Column(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
            SearchBar(query = searchQuery, onQueryChange = { searchQuery = it }, placeholder = "Favorilerimde Ara..."
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (searchQuery.length < 2) {

                    items(favoriteList) { movie ->
                        FavoriteMovieCard(
                            movie = movie,
                            onMovieClick = {
                                navController.navigate("detail/${movie.id}")
                            },
                            onRemoveClick = {

                            }
                        )
                    }

                } else {

                    if (filteredFavorites.isEmpty()) {

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
                                    text = "Favori Bulunamadı",
                                    style = MaterialTheme.typography.titleLarge
                                )

                                Text(
                                    text = "\"$searchQuery\" isimli favori bulunamadı.",
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
                                    text = "Arama Sonuçları",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "${filteredFavorites.size} film bulundu",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }

                        items(filteredFavorites) { movie ->

                            FavoriteMovieCard(
                                movie = movie,
                                onMovieClick = {
                                    navController.navigate("detail/${movie.id}")
                                },
                                onRemoveClick = {
                                    // Şimdilik boş bırak
                                }
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
fun FavoritesScreenPreview() {
    CineBee_Theme {
        EmptyFavoritesScreen(NavController(LocalContext.current))
    }

}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPrevieww() {
    CineBee_Theme {
        FavoriteList(navController = NavController(LocalContext.current))
    }

}
