package com.zawwinnaung.fitnesstracker.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import com.zawwinnaung.fitnesstracker.ui.components.MyNavigationBar

@Composable
fun HomeScreen(
    selectedKey: NavKey,
    onSelectedKey: (NavKey) -> Unit,
) {
    Scaffold(
        bottomBar = {
            MyNavigationBar(
                selectedKey = selectedKey,
                onSelectKey = { key ->
                    onSelectedKey(key)
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("Home")
        }
    }
}