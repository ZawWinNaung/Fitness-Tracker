package com.zawwinnaung.fitnesstracker.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TrackedActivity(
    val id: Int = 0,
    val activity: Activity,
    val time: Long,
    val routes: List<RoutePoint>,
    val timestamp: Long = System.currentTimeMillis()
)