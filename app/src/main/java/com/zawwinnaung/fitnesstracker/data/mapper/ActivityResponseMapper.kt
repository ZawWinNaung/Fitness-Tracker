package com.zawwinnaung.fitnesstracker.data.mapper

import com.zawwinnaung.fitnesstracker.R
import com.zawwinnaung.fitnesstracker.data.dto.ActivityResponseDto
import com.zawwinnaung.fitnesstracker.domain.model.Activity

fun List<ActivityResponseDto>.toDomain(): List<Activity> {
    return this.map {
        Activity(
            id = it.id,
            title = it.title,
            img = when (it.img_url) {
                "ic_walking" -> R.drawable.ic_walking
                "ic_running" -> R.drawable.ic_running
                "ic_cycling" -> R.drawable.ic_cycling
                else -> R.drawable.ic_placeholder
            }
        )
    }
}