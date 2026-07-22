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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.navigation3.runtime.NavKey
import com.zawwinnaung.fitnesstracker.BuildConfig
import com.zawwinnaung.fitnesstracker.navigation.MyNavigationBar
import com.zawwinnaung.fitnesstracker.ui.components.AppCard

@Composable
fun ProfileScreen(
    selectedKey: NavKey,
    onSelectedKey: (NavKey) -> Unit,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text("Profile", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

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
                        headlineContent = { Text("Update Profile") },
                        leadingContent = { Icon(Icons.Outlined.Edit, contentDescription = null) },
                        modifier = Modifier.clickable { onNavigateToUpdate() },
                        colors = ListItemDefaults.colors(Color.Transparent)
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { Text("Dark Mode") },
                        leadingContent = {
                            Icon(
                                Icons.Outlined.DarkMode,
                                contentDescription = null
                            )
                        },
                        colors = ListItemDefaults.colors(Color.Transparent),
                        trailingContent = {
                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = { viewModel.toggleTheme(it) }
                            )
                        }
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { Text("App Version") },
                        trailingContent = { Text(BuildConfig.VERSION_NAME, color = Color.Gray) },
                        leadingContent = { Icon(Icons.Outlined.Info, contentDescription = null) },
                        colors = ListItemDefaults.colors(Color.Transparent)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.logout() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Logout")
            }
        }
        if (uiState.errorMessage != null) {
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}