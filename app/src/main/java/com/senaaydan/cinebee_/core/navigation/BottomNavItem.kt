package com.senaaydan.cinebee_.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings


data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = "home",
        title = "Ana Sayfa",
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        route = "search",
        title = "Ara",
        icon = Icons.Default.Search
    ),
    BottomNavItem(
        route = "favorites",
        title = "Favoriler",
        icon = Icons.Default.Favorite
    ),
    BottomNavItem(
        route = "settings",
        title = "Ayarlar",
        icon = Icons.Default.Settings
    )
)