package com.zawwinnaung.fitnesstracker.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.zawwinnaung.fitnesstracker.ui.components.MyNavigationBar
import com.zawwinnaung.fitnesstracker.ui.screen.home.HomeScreen
import com.zawwinnaung.fitnesstracker.ui.screen.map.MapScreen
import com.zawwinnaung.fitnesstracker.ui.screen.profile.ProfileScreen
import com.zawwinnaung.fitnesstracker.ui.screen.tracking.TrackingScreen
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
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            NavDisplay(
                backStack = activeBackStack,
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                entryProvider = { key ->
                    when (key) {
                        is Route.Home -> {
                            NavEntry(key = key) {
                                HomeScreen(
                                    routeToTracking = { activity ->
                                        activeBackStack.add(Route.Tracking(activity))
                                    }
                                )
                            }
                        }

                        is Route.Profile -> {
                            NavEntry(key = key) {
                                ProfileScreen(
                                    onNavigateToUpdate = {
                                        activeBackStack.add(Route.UpdateProfile)
                                    }
                                )
                            }
                        }

                        is Route.UpdateProfile -> {
                            NavEntry(key = key) {
                                UpdateProfileScreen(
                                    onNavigateBack = {
                                        activeBackStack.remove(Route.UpdateProfile)
                                    }
                                )
                            }
                        }

                        is Route.Tracking -> {
                            NavEntry(key = key) {
                                TrackingScreen(
                                    activity = key.activity,
                                    onNavigateBack = { activity ->
                                        activeBackStack.remove(Route.Tracking(activity))
                                    })
                            }
                        }

                        is Route.Map -> {
                            NavEntry(key = key) {
                                MapScreen()
                            }
                        }

                        else -> throw RuntimeException("Invalid NavKey.")
                    }
                }
            )

            if (TOP_LEVEL_DESTINATIONS.contains(activeBackStack.last())) {
                MyNavigationBar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    selectedKey = currentTab,
                    onSelectKey = { key ->
                        currentTab = key
                    }
                )
            }
        }
    }

}