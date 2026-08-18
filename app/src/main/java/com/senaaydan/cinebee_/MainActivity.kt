package com.senaaydan.cinebee_

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.senaaydan.cinebee_.presentation.detail.DetailScreen
import com.senaaydan.cinebee_.presentation.detail.DetailViewModel
import com.senaaydan.cinebee_.presentation.favorites.FavoritesScreen
import com.senaaydan.cinebee_.presentation.favorites.FavoritesState
import com.senaaydan.cinebee_.presentation.favorites.FavoritesViewModel
import com.senaaydan.cinebee_.presentation.home.HomeViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.senaaydan.cinebee_.core.navigation.NavigationEvent
import com.senaaydan.cinebee_.ui.components.DetailTopBar
import com.senaaydan.cinebee_.ui.components.FavoritesTopBar
import com.senaaydan.cinebee_.ui.components.HomeTopBar
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.senaaydan.cinebee_.core.navigation.bottomNavItems
import com.senaaydan.cinebee_.presentation.detail.DetailRoute
import com.senaaydan.cinebee_.presentation.favorites.FavoritesRoute
import com.senaaydan.cinebee_.presentation.home.HomeRoute
import com.senaaydan.cinebee_.presentation.search.MovieSearchViewModel
import com.senaaydan.cinebee_.presentation.search.SearchRoute
import com.senaaydan.cinebee_.presentation.search.SearchScreen
import com.senaaydan.cinebee_.presentation.settings.EnglishStrings
import com.senaaydan.cinebee_.presentation.settings.LanguagePreference
import com.senaaydan.cinebee_.presentation.settings.SettingsScreen
import com.senaaydan.cinebee_.presentation.settings.SettingsViewModel
import com.senaaydan.cinebee_.presentation.settings.ThemePreference
import com.senaaydan.cinebee_.presentation.settings.TurkishStrings


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val settingsState by settingsViewModel.state.collectAsState()

            val darkTheme = when (settingsState.themePreference) {
                ThemePreference.SYSTEM -> isSystemInDarkTheme()
                ThemePreference.LIGHT -> false
                ThemePreference.DARK -> true
            }
            val strings = when (settingsState.languagePreference) {
                LanguagePreference.TURKISH -> TurkishStrings
                LanguagePreference.ENGLISH -> EnglishStrings
            }

            CineBee_Theme (darkTheme = darkTheme){


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )

                {

                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    var detailTitle by remember {
                        mutableStateOf("Film Detayı")
                    }
                    Scaffold(
                        containerColor = MaterialTheme.colorScheme.background,
                        topBar = {
                            when {
                                currentRoute == "home" -> {
                                    HomeTopBar(
                                        onFavoritesClick = {
                                            navController.navigate("favorites")
                                        }
                                    )
                                }

                                currentRoute == "favorites" -> {
                                    FavoritesTopBar(
                                        onBackClick = {
                                            navController.popBackStack()
                                        }, strings = strings
                                    )
                                }

                                currentRoute?.startsWith("detail") == true -> {
                                    DetailTopBar(
                                        title = detailTitle,
                                        onBackClick = {
                                            navController.popBackStack()
                                        },
                                        onFavoritesClick = {
                                            navController.navigate("favorites")
                                        }
                                    )
                                }
                            }

                        },
                        bottomBar = {

                            if (currentRoute?.startsWith("detail") != true) {

                                NavigationBar {

                                    bottomNavItems.forEach { item ->

                                        NavigationBarItem(

                                            selected =
                                                currentRoute == item.route,

                                            onClick = {

                                                navController.navigate(item.route) {

                                                    launchSingleTop = true

                                                    restoreState = true

                                                    popUpTo(
                                                        navController.graph.startDestinationId
                                                    ) {
                                                        saveState = true
                                                    }
                                                }
                                            },

                                            icon = {
                                                Icon(
                                                    imageVector = item.icon,
                                                    contentDescription = item.title
                                                )
                                            },

                                            label = {
                                                Text(item.title)
                                            }
                                        )
                                    }
                                }
                            }
                        }

                    )

                    { innerPadding ->

                        NavHost(

                            navController = navController,

                            startDestination = "home",
                            modifier = Modifier.padding(innerPadding)


                        ) {


                            composable("home") {

                                HomeRoute(

                                    navController=navController,strings=strings


                                    )

                            }
                            composable("search") {


                                SearchRoute(navController=navController,strings=strings)


                            }
                            composable("settings") {
                                val viewModel: SettingsViewModel = hiltViewModel()
                                val state by viewModel.state.collectAsState()

                                SettingsScreen(
                                    state = state,
                                    onIntent = viewModel::onIntent, strings = strings
                                )
                            }




                            composable("detail/{movieId}") { backStackEntry ->

                            val movieId =
                                backStackEntry.arguments?.getString("movieId")?.toIntOrNull()

                                DetailRoute(movieId = movieId!!,navController=navController,strings=strings, onTitleChanged = { title -> detailTitle = title })

                        }



                        composable("favorites") {

                            FavoritesRoute(
                                navController,
                                strings=strings
                            )

                        }


                    }
                }

                }

            }

        }
    }
}

