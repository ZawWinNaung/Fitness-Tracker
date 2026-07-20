package com.zawwinnaung.fitnesstracker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.zawwinnaung.fitnesstracker.domain.model.User
import com.zawwinnaung.fitnesstracker.screen.login.LoginScreen
import com.zawwinnaung.fitnesstracker.screen.register.RegisterScreen

@Composable
fun AuthNavigation() {
    val authBackStack = rememberNavBackStack(Route.Login)

    Scaffold() { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = authBackStack,
            entryProvider = { key ->
                when (key) {
                    is Route.Login -> NavEntry(key = key) {
                        LoginScreen(onNavigateToRegister = {
                            authBackStack.add(Route.Register)
                        })
                    }

                    is Route.Register -> NavEntry(key = key) {
                        RegisterScreen(onNavigateToLogin = {
                            if (authBackStack.isNotEmpty()) {
                                authBackStack.removeAt(authBackStack.lastIndex)
                            }
                        })
                    }

                    else -> throw RuntimeException("Invalid")
                }
            }
        )
    }
}