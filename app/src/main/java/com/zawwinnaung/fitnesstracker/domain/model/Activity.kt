package com.zawwinnaung.fitnesstracker.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Activity(
    val id: Int,
    val title: String,
    val img: Int
)