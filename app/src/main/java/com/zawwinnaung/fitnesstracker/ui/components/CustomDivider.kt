package com.zawwinnaung.fitnesstracker.ui.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CustomDivider(modifier: Modifier) {
    HorizontalDivider(modifier = modifier, color = Color.White.copy(alpha = 0.3f))
}