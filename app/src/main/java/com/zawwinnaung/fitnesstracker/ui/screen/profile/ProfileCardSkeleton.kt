package com.zawwinnaung.fitnesstracker.ui.screen.profile

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zawwinnaung.fitnesstracker.ui.components.AppCard

@Composable
fun ProfileCardSkeleton() {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val alpha by transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_alpha"
    )

    val skeletonColor = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)

    AppCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(skeletonColor)
            )

            Spacer(modifier = Modifier.height(8.dp))
            
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(skeletonColor)
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(10.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(skeletonColor)
                    )
                    Box(
                        modifier = Modifier
                            .width(90.dp)
                            .height(18.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(skeletonColor)
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(
                        modifier = Modifier
                            .width(45.dp)
                            .height(10.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(skeletonColor)
                    )
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(18.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(skeletonColor)
                    )
                }
            }
        }
    }
}