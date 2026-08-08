package com.zawwinnaung.fitnesstracker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val icon: ImageVector,
    val title: String
)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Route.Home to BottomNavItem(
        icon = Icons.Outlined.Home,
        title = "Home"
    ),
    Route.History to BottomNavItem(
        icon = Icons.Outlined.History,
        title = "History"
    ),
    Route.Profile to BottomNavItem(
        icon = Icons.Outlined.Person,
        title = "Profile"
    )
)