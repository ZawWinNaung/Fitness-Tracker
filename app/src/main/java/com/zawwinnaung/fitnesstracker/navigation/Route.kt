package com.zawwinnaung.fitnesstracker.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Login : Route, NavKey

    @Serializable
    data object Register : Route, NavKey

    @Serializable
    data object Home : Route, NavKey

    @Serializable
    data object Profile : Route, NavKey
}