package com.zawwinnaung.fitnesstracker.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.zawwinnaung.fitnesstracker.screen.profile.ProfileScreen

@Composable
fun MainNavigation(onLogout: () -> Unit) {
    val homeBackStack = rememberNavBackStack(Route.Home)
    val settingsBackStack = rememberNavBackStack(Route.Profile)

    var currentTab by remember { mutableStateOf<NavKey>(Route.Home) }

    BackHandler(enabled = currentTab != Route.Home) {
        currentTab = Route.Home
    }

    val activeBackStack = when (currentTab) {
        is Route.Home -> homeBackStack
        is Route.Profile -> settingsBackStack
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
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = activeBackStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = { key ->
                when (key) {
                    is Route.Home -> {
                        NavEntry(key = key) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Home")
                            }
                        }
                    }

                    is Route.Profile -> {
                        NavEntry(key = key) {
                            ProfileScreen(
                                onNavigateToUpdate = {}
                            )
                        }
                    }

                    else -> throw RuntimeException("Invalid NavKey.")
                }
            }
        )
    }
}