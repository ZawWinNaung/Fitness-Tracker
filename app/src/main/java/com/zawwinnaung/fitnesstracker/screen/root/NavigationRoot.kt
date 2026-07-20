package com.zawwinnaung.fitnesstracker.screen.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zawwinnaung.fitnesstracker.navigation.AuthNavigation
import com.zawwinnaung.fitnesstracker.navigation.MainNavigation
import com.zawwinnaung.fitnesstracker.ui.theme.FitnessTrackerTheme

@Composable
fun NavigationRoot(
    viewModel: NavigationRootViewModel = hiltViewModel()
) {

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    FitnessTrackerTheme(isDarkTheme) {
        if (!isLoggedIn) {
            AuthNavigation()
        } else {
            MainNavigation(onLogout = { viewModel.logout() })
        }
    }

}