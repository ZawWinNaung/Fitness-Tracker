package com.zawwinnaung.fitnesstracker.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zawwinnaung.fitnesstracker.BuildConfig
import com.zawwinnaung.fitnesstracker.ui.components.AppCard
import com.zawwinnaung.fitnesstracker.ui.components.CustomButton
import com.zawwinnaung.fitnesstracker.ui.components.CustomDivider

@Composable
fun ProfileScreen(
    onNavigateToUpdate: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUser()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        if (uiState.isLoading) {
            ProfileCardSkeleton()
        } else {
            ProfileCard(uiState.user)

        }

        Spacer(modifier = Modifier.height(24.dp))

        AppCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                ListItem(
                    headlineContent = {
                        Text(
                            "Update Profile",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    leadingContent = {
                        Icon(
                            Icons.Outlined.Edit,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.clickable { onNavigateToUpdate() },
                    colors = ListItemDefaults.colors(Color.Transparent)
                )
                CustomDivider(modifier = Modifier.padding(horizontal = 20.dp))
                ListItem(
                    headlineContent = {
                        Text(
                            "Dark Mode",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    leadingContent = {
                        Icon(
                            Icons.Outlined.DarkMode,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = null
                        )
                    },
                    colors = ListItemDefaults.colors(Color.Transparent),
                    trailingContent = {
                        Switch(
                            colors = SwitchDefaults.colors(
                                checkedTrackColor = MaterialTheme.colorScheme.onSurface,
                                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface
                            ),
                            checked = isDarkTheme,
                            onCheckedChange = { viewModel.toggleTheme(it) }
                        )
                    }
                )
                CustomDivider(modifier = Modifier.padding(horizontal = 20.dp))
                ListItem(
                    headlineContent = {
                        Text(
                            "App Version",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    trailingContent = {
                        Text(
                            BuildConfig.VERSION_NAME,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    leadingContent = {
                        Icon(
                            Icons.Outlined.Info,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = null
                        )
                    },
                    colors = ListItemDefaults.colors(Color.Transparent)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            onClick = { viewModel.logout() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Text("Logout", color = MaterialTheme.colorScheme.onSurface)
        }
    }
    if (uiState.errorMessage != null) {
        Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_LONG).show()
    }
}