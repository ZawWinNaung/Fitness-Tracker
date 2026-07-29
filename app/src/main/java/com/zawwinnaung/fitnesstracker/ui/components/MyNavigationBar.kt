package com.zawwinnaung.fitnesstracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.zawwinnaung.fitnesstracker.navigation.TOP_LEVEL_DESTINATIONS

@Composable
fun MyNavigationBar(
    modifier: Modifier = Modifier,
    selectedKey: NavKey,
    onSelectKey: (NavKey) -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(32.dp),
                    clip = false
                ),
        ) {
            Surface(
                modifier = Modifier,
                shape = RoundedCornerShape(32.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
            ) {
                Row(
                    modifier = Modifier
                        .height(64.dp)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
        }
    }
}