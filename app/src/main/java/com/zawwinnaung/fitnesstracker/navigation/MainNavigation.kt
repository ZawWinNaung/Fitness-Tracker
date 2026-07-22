package com.zawwinnaung.fitnesstracker.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.zawwinnaung.fitnesstracker.ui.screen.home.HomeScreen
import com.zawwinnaung.fitnesstracker.ui.screen.profile.ProfileScreen
import com.zawwinnaung.fitnesstracker.ui.screen.updateprofile.UpdateProfileScreen

@Composable
fun MainNavigation() {
    val homeBackStack = rememberNavBackStack(Route.Home)
    val profileBackStack = rememberNavBackStack(Route.Profile)

    var currentTab by remember { mutableStateOf<NavKey>(Route.Home) }

    BackHandler(enabled = currentTab != Route.Home) {
        currentTab = Route.Home
    }

    val activeBackStack = when (currentTab) {
        is Route.Home -> homeBackStack
        is Route.Profile -> profileBackStack
        else -> homeBackStack
    }

    Scaffold(
        bottomBar = {
            MyNavigationBar(
                selectedKey = currentTab,
                onSelectKey = { key ->
                    currentTab = key
                }
            )
        }
    ) { paddingValues ->
        NavDisplay(
            modifier = Modifier.padding(paddingValues = paddingValues),
            backStack = activeBackStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = { key ->
                when (key) {
                    is Route.Home -> {
                        NavEntry(key = key) {
                            HomeScreen()
                        }
                    }

                    is Route.Profile -> {
                        NavEntry(key = key) {
                            ProfileScreen(
                                onNavigateToUpdate = {
                                    profileBackStack.add(Route.UpdateProfile)
                                }
                            )
                        }
                    }

                    is Route.UpdateProfile -> {
                        NavEntry(key = key) {
                            UpdateProfileScreen(
                                onNavigateBack = {
                                    profileBackStack.remove(Route.UpdateProfile)
                                }
                            )
                        }
                    }

                    else -> throw RuntimeException("Invalid NavKey.")
                }
            }
        )
    }


}