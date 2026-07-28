package com.zawwinnaung.fitnesstracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { onNavigationIconClick() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}