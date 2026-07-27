package com.zawwinnaung.fitnesstracker.ui.screen.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zawwinnaung.fitnesstracker.navigation.AuthNavigation
import com.zawwinnaung.fitnesstracker.navigation.MainNavigation
import com.zawwinnaung.fitnesstracker.ui.components.GradientBackground
import com.zawwinnaung.fitnesstracker.ui.theme.FitnessTrackerTheme

@Composable
fun NavigationRoot(
    viewModel: NavigationRootViewModel = hiltViewModel()
) {

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    FitnessTrackerTheme(isDarkTheme) {
        GradientBackground {
            if (!isLoggedIn) {
                AuthNavigation()
            } else {
                MainNavigation()
            }
        }
    }

}