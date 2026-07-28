package com.zawwinnaung.fitnesstracker.ui.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import com.zawwinnaung.fitnesstracker.navigation.TOP_LEVEL_DESTINATIONS

@Composable
fun MyNavigationBar(
    selectedKey: NavKey,
    onSelectKey: (NavKey) -> Unit
) {
    BottomAppBar() {
        TOP_LEVEL_DESTINATIONS.forEach { (topLevelDestination, data) ->
            NavigationBarItem(
                selected = topLevelDestination == selectedKey,
                onClick = {
                    onSelectKey(topLevelDestination)
                },
                icon = {
                    Icon(
                        imageVector = data.icon,
                        contentDescription = data.title
                    )
                },
                label = {
                    Text(text = data.title)
                }
            )
        }
    }
}